/*
 * Attribution
 * CC BY
 * This license lets others distribute, remix, tweak,
 * and build upon your work, even commercially,
 * as long as they credit you for the original creation.
 * This is the most accommodating of licenses offered.
 * Recommended for maximum dissemination and use of licensed materials.
 *
 * http://creativecommons.org/licenses/by/3.0/
 * http://creativecommons.org/licenses/by/3.0/legalcode
 */
package com.thjug.bgile.faces;

import com.thjug.bgile.entity.Converterable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import java.util.List;
import javax.faces.convert.FacesConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author @nuboat
 */
@FacesConverter("DefaultConverter")
public class DefaultConverter implements Converter {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultConverter.class);

	private static final String LIST_ATTRIBUTE = "listArrtibute";

	@Override
	public Object getAsObject(final FacesContext facesContext, final UIComponent component, final String value) {
		final List<Converterable> converters = (List<Converterable>) component.getAttributes().get(LIST_ATTRIBUTE);
		for (final Converterable converter : converters)
			if (converter.getItemValue().equals(value))
				return converter;

		return null;
	}

	@Override
	public String getAsString(final FacesContext facesContext, final UIComponent component, final Object value) {
		final Converterable entity = (Converterable) value;
		return entity.getItemValue();
	}

}