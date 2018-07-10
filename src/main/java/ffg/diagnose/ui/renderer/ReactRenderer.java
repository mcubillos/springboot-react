package ffg.diagnose.ui.renderer;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.script.Invocable;
import javax.script.ScriptException;

@Component
@SuppressWarnings("restriction")
public class  ReactRenderer extends BasePooledObjectFactory<Invocable> {
    
	@Autowired
    private ResourceLoader resourceLoader;

    @Value("${application.renderEngine.minIdle:4}")
    private Integer minIdle;

    @Value("${application.renderEngine.maxIdle:4}")
    private Integer maxIdle;

    @Value("${application.renderEngine.maxTotal:4}")
    private Integer maxTotal;

    private ObjectPool<Invocable> engines;

    private NashornScriptEngineFactory nashornScriptEngineFactory;

    private String markup;

    private String readStream(Resource resource) throws IOException {
        InputStream inputStream = resource.getInputStream();
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return new String(bytes);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    @PostConstruct
    public void init() throws Exception {
    	GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        engines = new GenericObjectPool<Invocable>(this, config);
        nashornScriptEngineFactory = new NashornScriptEngineFactory();

        markup = readStream(resourceLoader.getResource("classpath:/templates/index.html"));
    }

    private void feedScript(NashornScriptEngine engine, String script) throws IOException,ScriptException {
    	InputStreamReader inputStream =  new InputStreamReader(resourceLoader.getResource("classpath:/" + script).getInputStream());
        try {
            engine.eval(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    @Override 
    public Invocable create() {
    	NashornScriptEngine nashornScriptEngine = (NashornScriptEngine) nashornScriptEngineFactory.getScriptEngine();
        try {
            feedScript(nashornScriptEngine, "script/polyfill.js");
            feedScript(nashornScriptEngine, "script/render.js");
            feedScript(nashornScriptEngine, "script/server.js");
        } catch (ScriptException e) {
            throw new RuntimeException("unable to load initial scripts", e);
        } catch (IOException e) {
            throw new RuntimeException("unable to load initial scripts", e);
        }

        return nashornScriptEngine;
    }

    @Override
    public PooledObject<Invocable> wrap(Invocable invocable) {
        return new DefaultPooledObject<Invocable>(invocable);
    }

    public String render(Map<String, Object> modelMap) throws Exception{
    	Invocable invocable = engines.borrowObject();
        try {
            Object html = invocable.invokeFunction("render", markup, modelMap);
            return (String)html;
        } finally {
            if (invocable != null) {
                engines.returnObject(invocable);
            }
        }
    }
}