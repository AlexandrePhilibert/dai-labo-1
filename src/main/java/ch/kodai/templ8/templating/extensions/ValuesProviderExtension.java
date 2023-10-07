package ch.kodai.templ8.templating.extensions;

import io.pebbletemplates.pebble.attributes.AttributeResolver;
import io.pebbletemplates.pebble.attributes.ResolvedAttribute;
import io.pebbletemplates.pebble.extension.AbstractExtension;
import io.pebbletemplates.pebble.node.ArgumentsNode;
import io.pebbletemplates.pebble.template.EvaluationContextImpl;

import java.util.List;

/**
 * This class implements a Pebble extension.
 * Pebble is the templating system in use for the following reasons:
 * <ul>
 *     <li>Its syntax is readable, and near the one in the go standard library, facilitating migration</li>
 *     <li>It is really extensible, allowing for custom elements traversal, among other modifications</li>
 * </ul>
 */
public class ValuesProviderExtension extends AbstractExtension {
    /**
     * Attribute resolver for our {@link ValueRoot} element.
     */
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
                // You may be wondering why we need a custom attribute resolver instead of a map or any other solution.
                // This is mainly due to Pebble's use of reflection, that tries to access the (get)?`attributeNameValue`
                // function, that we cannot reasonably implement dynamically
                if (attributeNameValue instanceof String stringName) {
                    return new ResolvedAttribute(root.get(stringName));
                } else {
                    // TODO: Support arrays
                    //  This is more than doable, but needs a more performant parser-combinator than the current
                    //  solution, that is basically iterate over the dots.
                    throw new RuntimeException("Arrays are not supported in this version");
                }
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
