package ch.kodai.templ8.templating.extensions;

import io.pebbletemplates.pebble.attributes.AttributeResolver;
import io.pebbletemplates.pebble.attributes.ResolvedAttribute;
import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.node.ArgumentsNode;
import io.pebbletemplates.pebble.template.EvaluationContextImpl;

import java.util.List;

public class ValuesProviderExtension extends AbstractExtension {
    public static class ValueAttributeResolver implements AttributeResolver {

        @Override
        public ResolvedAttribute resolve(
                Object instance,
                Object attributeNameValue,
                Object[] argumentValues,
                ArgumentsNode args,
                EvaluationContextImpl context,
                String filename,
                int lineNumber) {
            if (instance instanceof ValueRoot root) {
                return new ResolvedAttribute(root.get((String) attributeNameValue));
            }
            return null;
        }

    }

    @Override
    public List<AttributeResolver> getAttributeResolver() {
        // AttributeResolver for properties.
        return List.of(
                new ValueAttributeResolver()
        );
    }
}
