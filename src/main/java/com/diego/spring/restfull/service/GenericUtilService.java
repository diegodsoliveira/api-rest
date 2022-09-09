package com.diego.spring.restfull.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class GenericUtilService implements Serializable {

    private static final long serialVersionUID = 5230997880378013120L;

    public StringBuilder consultaCep(String cep) throws Exception {

        if (cep != null) {

            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            String endereco = "";
            StringBuilder jsonCep = new StringBuilder();

            while ((endereco = bufferedReader.readLine()) != null) {
                jsonCep.append(endereco);
            }

            return jsonCep;
        }
        return null;
    }

}
