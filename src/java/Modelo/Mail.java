/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Properties;

/**
 *
 * @author Oscar
 */
public class Mail {

    private String host;
    private String port;

    private boolean debug;

    private Properties props;

    public Mail(String host, String port) {
        this.host = host;
        this.port = port;

        /**
         * Get a Properties object
         */
        props = System.getProperties();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }
    
    
}
