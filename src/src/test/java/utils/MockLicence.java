package utils;

import com.workshop.licence.Licence;
import com.workshop.licence.LicenceType;

public class MockLicence {

    public static Licence mock(LicenceType licenceType, double value) {
        Licence licence = new Licence();
        licence.setLicenceType(licenceType);
        licence.setValue(value);
        return licence;
    }
}
