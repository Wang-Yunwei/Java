package com.mdsd.cloud.config;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author WangYunwei [2022-08-23]
 */
public class TrustingSSLSocketFactory extends SSLSocketFactory
        implements X509TrustManager, X509KeyManager {

    private static final Map<String, SSLSocketFactory> sslSocketFactories =
            new LinkedHashMap<String, SSLSocketFactory>();

    private static final char[] KEYSTORE_PASSWORD = "password".toCharArray();

    private final static String[] ENABLED_CIPHER_SUITES = {"TLS_RSA_WITH_AES_256_CBC_SHA"};

    private final SSLSocketFactory delegate;

    private final String serverAlias;

    private final PrivateKey privateKey;

    private final X509Certificate[] certificateChain;

    private TrustingSSLSocketFactory(final String serverAlias) {

        try {
            final SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(new KeyManager[]{this}, new TrustManager[]{this}, new SecureRandom());
            this.delegate = sc.getSocketFactory();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        this.serverAlias = serverAlias;
        if (serverAlias.isEmpty()) {
            this.privateKey = null;
            this.certificateChain = null;
        } else {
            try {
                final KeyStore keyStore =
                        loadKeyStore(TrustingSSLSocketFactory.class.getResourceAsStream("/javasign.p12"));
                this.privateKey = (PrivateKey) keyStore.getKey(serverAlias, KEYSTORE_PASSWORD);
                final Certificate[] rawChain = keyStore.getCertificateChain(serverAlias);
                this.certificateChain = Arrays.copyOf(rawChain, rawChain.length, X509Certificate[].class);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static SSLSocketFactory get() {

        return get("");
    }

    public synchronized static SSLSocketFactory get(final String serverAlias) {

        if (!sslSocketFactories.containsKey(serverAlias)) {
            sslSocketFactories.put(serverAlias, new TrustingSSLSocketFactory(serverAlias));
        }
        return sslSocketFactories.get(serverAlias);
    }

    static Socket setEnabledCipherSuites(final Socket socket) {

        SSLSocket.class.cast(socket).setEnabledCipherSuites(ENABLED_CIPHER_SUITES);
        return socket;
    }

    private static KeyStore loadKeyStore(final InputStream inputStream) throws IOException {

        try {
            final KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(inputStream, KEYSTORE_PASSWORD);
            return keyStore;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        } finally {
            inputStream.close();
        }
    }

    @Override
    public String[] getDefaultCipherSuites() {

        return ENABLED_CIPHER_SUITES;
    }

    @Override
    public String[] getSupportedCipherSuites() {

        return ENABLED_CIPHER_SUITES;
    }

    @Override
    public Socket createSocket(final Socket s, final String host, final int port, final boolean autoClose)
            throws IOException {

        return setEnabledCipherSuites(this.delegate.createSocket(s, host, port, autoClose));
    }

    @Override
    public Socket createSocket(final String host, final int port) throws IOException {

        return setEnabledCipherSuites(this.delegate.createSocket(host, port));
    }

    @Override
    public Socket createSocket(final InetAddress host, final int port) throws IOException {

        return setEnabledCipherSuites(this.delegate.createSocket(host, port));
    }

    @Override
    public Socket createSocket(final String host, final int port, final InetAddress localHost, final int localPort)
            throws IOException {

        return setEnabledCipherSuites(this.delegate.createSocket(host, port, localHost, localPort));
    }

    @Override
    public Socket createSocket(final InetAddress address, final int port, final InetAddress localAddress, final int localPort)
            throws IOException {

        return setEnabledCipherSuites(this.delegate.createSocket(address, port, localAddress, localPort));
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {

        return null;
    }

    @Override
    public void checkClientTrusted(final X509Certificate[] certs, final String authType) {

    }

    @Override
    public void checkServerTrusted(final X509Certificate[] certs, final String authType) {

    }

    @Override
    public String[] getClientAliases(final String keyType, final Principal[] issuers) {

        return null;
    }

    @Override
    public String chooseClientAlias(final String[] keyType, final Principal[] issuers, final Socket socket) {

        return null;
    }

    @Override
    public String[] getServerAliases(final String keyType, final Principal[] issuers) {

        return null;
    }

    @Override
    public String chooseServerAlias(final String keyType, final Principal[] issuers, final Socket socket) {

        return this.serverAlias;
    }

    @Override
    public X509Certificate[] getCertificateChain(final String alias) {

        return this.certificateChain;
    }

    @Override
    public PrivateKey getPrivateKey(final String alias) {

        return this.privateKey;
    }
}
