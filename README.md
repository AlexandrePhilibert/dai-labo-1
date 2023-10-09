# Templ8

![Demo](./demos/demo.svg)

Templ8 is a templating tool that is meant to be used to generate Kubernetes YAML files.
It is meant to reduce the time spent copy and pasting values in files.

## Running the CLI

Without arguments:

```
java -jar target/templ8-1.0-SNAPSHOT.jar
```

Using the example folder:

```
java -jar target/templ8-1.0-SNAPSHOT.jar -f examples/template.yaml -v examples/values.yaml -o examples/output.yaml
```

The output will be located in the `/examples` folder.

## Building the CLI

```
./mvnw clean package
```

## This product is a work in progress !
This project is still in its early stages, expect a lot of things breaking, or corner cases not covered.

Here is a non-exhaustive list of things to fix:
- [ ] Add support for arrays (#11)
- [ ] Add better error logging if possible (#17)