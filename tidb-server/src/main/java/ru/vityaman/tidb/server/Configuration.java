package ru.vityaman.tidb.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.exceptions.YamlReadingException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public final class Configuration {
    private final static String EXAMPLE = new StringBuilder()
    .append("configuration:\n")
    .append("  connection:\n")
    .append("    host: localhost\n")
    .append("    port: 2222\n")
    .toString();

    @Getter
    private final Connection connection;

    private Configuration(@NonNull YamlMapping yaml) 
    throws UnknownHostException, YamlReadingException 
    {
        yaml = yaml.yamlMapping("configuration");
        connection = new Connection(yaml.yamlMapping("connection"));
    }

    public static Configuration load(@NonNull Path path) throws ReadingException {
        try {
            return new Configuration(
                Yaml.createYamlInput(path.toFile()).readYamlMapping()
            );
        } catch (YamlReadingException | IOException e) {
            throw new ReadingException(e);
        } catch (NullPointerException e) {
            throw new ReadingException(String.format(
                "Invalid yaml file structure. Example:\n---\n%s\n---",
                EXAMPLE
            ), e);
        }
    }

    @ToString
    @EqualsAndHashCode
    public static final class Connection {
        @Getter 
        private final InetSocketAddress address;

        private Connection(@NonNull YamlMapping yaml) 
        throws UnknownHostException, YamlReadingException
        {
            address = new InetSocketAddress(
                InetAddress.getByName(
                    yaml.string("host")
                ), 
                yaml.integer("port")
            );
        }
    }

    public static class ReadingException extends Exception {
        public ReadingException(String message, Throwable cause) {
            super(String.format(
                "Error while reading yaml config: %s",
                message
            ), cause);
        }

        public ReadingException(Throwable cause) {
            this(cause.getMessage(), cause);
        }
    }
}
