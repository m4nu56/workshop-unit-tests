package utils;

import com.workshop.Licence;
import com.workshop.LicenceType;

public class MockLicence {

    public static Licence mock(LicenceType licenceType, double value) {
        Licence licence = new Licence();
        licence.setLicenceType(licenceType);
        licence.setValue(value);
        return licence;
    }
}
