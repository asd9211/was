package com.example.http;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class VirtualHost {

    private static Logger log = LoggerFactory.getLogger(VirtualHost.class);

    static {
        try (InputStream stream = VirtualHost.class.getResourceAsStream("/application.json")) {
            String application = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            config = new JSONObject(application);

        } catch (IOException e) {
            log.error("VirtualHost application.json loading fail!", e);
        }
    }

    private File documentRoot;
    private String indexFileName;
    private Map<Integer, String> errorPage = new HashMap<>();
    private static JSONObject config;

    public VirtualHost(HttpRequest request) {
        init(request);
    }

    private void init(HttpRequest request) {
        JSONArray httpRoots = config.getJSONArray("HTTP_ROOT");

        for (Object httpRoot : httpRoots) {
            JSONObject root = (JSONObject) httpRoot;
            String host = root.getString("host");

            if (request.getHost().equals(host)) {
                this.documentRoot = new File(root.getString("documentRoot"));
                this.indexFileName = root.getString("indexFileName");
                JSONArray errors = root.getJSONArray("error");

                for (Object obj : errors) {
                    JSONObject error = (JSONObject) obj;
                    this.errorPage.put(error.getInt("code"), error.getString("page"));
                }
            }
        }
    }

    public String getIndexFileName() {
        return indexFileName;
    }

    public Map<Integer, String> getErrorPage(){
        return errorPage;
    }

    public String getErrorPageHtml(HttpStatus httpStatus) throws IOException {
        int statusCode = httpStatus.getCode();
        String pageName = errorPage.get(statusCode);
        String errorPagePath = documentRoot + File.separator + pageName;
        Path path = Paths.get(errorPagePath);

        if (Files.exists(path)) {
            return Files.readString(path, StandardCharsets.UTF_8);
        }

        // Virtual Host로 지정한 경로에 파일이 없을 경우 default 파일로 반환
        log.info("Virtual Host Page Not Exists! path : {}", path);
        try (InputStream stream = getClass().getResourceAsStream("/" + statusCode + ".html")) {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public String getIndexPageHtml() throws IOException {
        String indexPagePath = documentRoot + File.separator + indexFileName;
        Path path = Paths.get(indexPagePath);

        if (Files.exists(path)) {
            return Files.readString(path, StandardCharsets.UTF_8);
        }

        // Virtual Host로 지정한 경로에 파일이 없을 경우 default 파일로 반환
        log.info("Virtual Host Page Not Exists! path : {}", path);
        try (InputStream stream = getClass().getResourceAsStream("/index.html")) {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }


    @Override
    public String toString() {
        return "VirtualHost{" +
                "documentRoot=" + documentRoot +
                ", indexFileName='" + indexFileName + '\'' +
                ", errorPage=" + errorPage +
                '}';
    }
}
