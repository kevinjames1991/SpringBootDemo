
import com.ma.generator.CodeGenerator;
import com.ma.generator.GeneratorMain;
import com.ma.generator.config.CodeGenConfiguration;
import com.ma.generator.config.CodeGeneratorConfigParser;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.exception.XMLParserException;

import java.io.*;
import java.net.URL;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

public class CodeGenMain {
    public static void main(String[] args) {
        try {
            String projectPath = new File(".").getCanonicalPath();
            InputStream inputStream = getInputStream(args);
            CodeGeneratorConfigParser cp = new CodeGeneratorConfigParser();
            CodeGenConfiguration config = cp.parseConfiguration(inputStream);
            config.setTargetProject(projectPath + config.getTargetProject());
            Configuration configuration = config.buildConfiguration();
            CodeGenerator generator = new CodeGenerator(configuration);
            generator.generate();

        } catch (XMLParserException e) {
            System.out.println(getString("Progress.3")); //$NON-NLS-1$
            for (String error : e.getErrors()) {
                System.out.println(error);
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static InputStream getInputStream(String[] args) throws FileNotFoundException {
        InputStream inputStream = null;
        if (args != null && args.length > 0) {
            inputStream = new FileInputStream(new File(args[0].trim()));
        } else {
            String pathname = "../code-generator-config.xml";
            File file = new File(pathname);
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            }
        }
        if (inputStream == null) {
            inputStream = getResource("code-generator-config.xml");
            if (inputStream == null) {
                throw new FileNotFoundException("code-generator-config.xml");
            }
        }
        return inputStream;
    }

    public static InputStream getResource(String resource) {
        ClassLoader classLoader = GeneratorMain.class.getClassLoader();
        URL url = classLoader.getResource(resource);
        if (url != null) {
            try {
                return url.openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(resource + " not found!");
        }
        InputStream inputStream = classLoader.getResourceAsStream(resource);
        if (inputStream == null) {
            System.out.println(resource + " not found");
        } else {
            System.out.println(resource + " found");
        }
        return inputStream;
    }
}
