package org.dashbuilder.validations.dataset;

import com.google.gwtmockito.GwtMockitoTestRunner;
import org.dashbuilder.dataset.def.PrometheusDataSetDef;
import org.dashbuilder.dataset.validation.groups.PrometheusDataSetDefValidation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(GwtMockitoTestRunner.class)
public class PrometheusDataSetDefValidatorTest extends AbstractValidationTest {

    @Mock
    PrometheusDataSetDef prometheusDataSetDef;
    private PrometheusDataSetDefValidator tested;

    @Before
    public void setup() {
        super.setup();
        tested = spy(new PrometheusDataSetDefValidator(validator));
    }

    @Test
    public void testValidate() {
        tested.validateCustomAttributes(prometheusDataSetDef);
        verify(validator).validate(prometheusDataSetDef, PrometheusDataSetDefValidation.class);
    }
}
