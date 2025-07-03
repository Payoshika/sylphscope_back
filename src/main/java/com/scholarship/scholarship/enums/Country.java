package com.scholarship.scholarship.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public enum Country {
    AD("AD", "Andorra", "🇦🇩", "+376"),
    AE("AE", "United Arab Emirates", "🇦🇪", "+971"),
    AF("AF", "Afghanistan", "🇦🇫", "+93"),
    AG("AG", "Antigua and Barbuda", "🇦🇬", "+1"),
    AI("AI", "Anguilla", "🇦🇮", "+1"),
    AL("AL", "Albania", "🇦🇱", "+355"),
    AM("AM", "Armenia", "🇦🇲", "+374"),
    AO("AO", "Angola", "🇦🇴", "+244"),
    AQ("AQ", "Antarctica", "🇦🇶", "+672"),
    AR("AR", "Argentina", "🇦🇷", "+54"),
    AS("AS", "American Samoa", "🇦🇸", "+1"),
    AT("AT", "Austria", "🇦🇹", "+43"),
    AU("AU", "Australia", "🇦🇺", "+61"),
    AW("AW", "Aruba", "🇦🇼", "+297"),
    AX("AX", "Åland Islands", "🇦🇽", "+358"),
    AZ("AZ", "Azerbaijan", "🇦🇿", "+994"),
    BA("BA", "Bosnia and Herzegovina", "🇧🇦", "+387"),
    BB("BB", "Barbados", "🇧🇧", "+1"),
    BD("BD", "Bangladesh", "🇧🇩", "+880"),
    BE("BE", "Belgium", "🇧🇪", "+32"),
    BF("BF", "Burkina Faso", "🇧🇫", "+226"),
    BG("BG", "Bulgaria", "🇧🇬", "+359"),
    BH("BH", "Bahrain", "🇧🇭", "+973"),
    BI("BI", "Burundi", "🇧🇮", "+257"),
    BJ("BJ", "Benin", "🇧🇯", "+229"),
    BL("BL", "Saint Barthélemy", "🇧🇱", "+590"),
    BM("BM", "Bermuda", "🇧🇲", "+1"),
    BN("BN", "Brunei", "🇧🇳", "+673"),
    BO("BO", "Bolivia", "🇧🇴", "+591"),
    BQ("BQ", "Caribbean Netherlands", "🇧🇶", "+599"),
    BR("BR", "Brazil", "🇧🇷", "+55"),
    BS("BS", "Bahamas", "🇧🇸", "+1"),
    BT("BT", "Bhutan", "🇧🇹", "+975"),
    BV("BV", "Bouvet Island", "🇧🇻", "+47"),
    BW("BW", "Botswana", "🇧🇼", "+267"),
    BY("BY", "Belarus", "🇧🇾", "+375"),
    BZ("BZ", "Belize", "🇧🇿", "+501"),
    CA("CA", "Canada", "🇨🇦", "+1"),
    CC("CC", "Cocos (Keeling) Islands", "🇨🇨", "+61"),
    CD("CD", "Democratic Republic of the Congo", "🇨🇩", "+243"),
    CF("CF", "Central African Republic", "🇨🇫", "+236"),
    CG("CG", "Republic of the Congo", "🇨🇬", "+242"),
    CH("CH", "Switzerland", "🇨🇭", "+41"),
    CI("CI", "Côte d'Ivoire", "🇨🇮", "+225"),
    CK("CK", "Cook Islands", "🇨🇰", "+682"),
    CL("CL", "Chile", "🇨🇱", "+56"),
    CM("CM", "Cameroon", "🇨🇲", "+237"),
    CN("CN", "China", "🇨🇳", "+86"),
    CO("CO", "Colombia", "🇨🇴", "+57"),
    CR("CR", "Costa Rica", "🇨🇷", "+506"),
    CU("CU", "Cuba", "🇨🇺", "+53"),
    CV("CV", "Cape Verde", "🇨🇻", "+238"),
    CW("CW", "Curaçao", "🇨🇼", "+599"),
    CX("CX", "Christmas Island", "🇨🇽", "+61"),
    CY("CY", "Cyprus", "🇨🇾", "+357"),
    CZ("CZ", "Czech Republic", "🇨🇿", "+420"),
    DE("DE", "Germany", "🇩🇪", "+49"),
    DJ("DJ", "Djibouti", "🇩🇯", "+253"),
    DK("DK", "Denmark", "🇩🇰", "+45"),
    DM("DM", "Dominica", "🇩🇲", "+1"),
    DO("DO", "Dominican Republic", "🇩🇴", "+1"),
    DZ("DZ", "Algeria", "🇩🇿", "+213"),
    EC("EC", "Ecuador", "🇪🇨", "+593"),
    EE("EE", "Estonia", "🇪🇪", "+372"),
    EG("EG", "Egypt", "🇪🇬", "+20"),
    EH("EH", "Western Sahara", "🇪🇭", "+212"),
    ER("ER", "Eritrea", "🇪🇷", "+291"),
    ES("ES", "Spain", "🇪🇸", "+34"),
    ET("ET", "Ethiopia", "🇪🇹", "+251"),
    FI("FI", "Finland", "🇫🇮", "+358"),
    FJ("FJ", "Fiji", "🇫🇯", "+679"),
    FK("FK", "Falkland Islands", "🇫🇰", "+500"),
    FM("FM", "Micronesia", "🇫🇲", "+691"),
    FO("FO", "Faroe Islands", "🇫🇴", "+298"),
    FR("FR", "France", "🇫🇷", "+33"),
    GA("GA", "Gabon", "🇬🇦", "+241"),
    GB("GB", "United Kingdom", "🇬🇧", "+44"),
    GD("GD", "Grenada", "🇬🇩", "+1"),
    GE("GE", "Georgia", "🇬🇪", "+995"),
    GF("GF", "French Guiana", "🇬🇫", "+594"),
    GG("GG", "Guernsey", "🇬🇬", "+44"),
    GH("GH", "Ghana", "🇬🇭", "+233"),
    GI("GI", "Gibraltar", "🇬🇮", "+350"),
    GL("GL", "Greenland", "🇬🇱", "+299"),
    GM("GM", "Gambia", "🇬🇲", "+220"),
    GN("GN", "Guinea", "🇬🇳", "+224"),
    GP("GP", "Guadeloupe", "🇬🇵", "+590"),
    GQ("GQ", "Equatorial Guinea", "🇬🇶", "+240"),
    GR("GR", "Greece", "🇬🇷", "+30"),
    GS("GS", "South Georgia and the South Sandwich Islands", "🇬🇸", "+500"),
    GT("GT", "Guatemala", "🇬🇹", "+502"),
    GU("GU", "Guam", "🇬🇺", "+1"),
    GW("GW", "Guinea-Bissau", "🇬🇼", "+245"),
    GY("GY", "Guyana", "🇬🇾", "+592"),
    HK("HK", "Hong Kong", "🇭🇰", "+852"),
    HM("HM", "Heard Island and McDonald Islands", "🇭🇲", "+672"),
    HN("HN", "Honduras", "🇭🇳", "+504"),
    HR("HR", "Croatia", "🇭🇷", "+385"),
    HT("HT", "Haiti", "🇭🇹", "+509"),
    HU("HU", "Hungary", "🇭🇺", "+36"),
    ID("ID", "Indonesia", "🇮🇩", "+62"),
    IE("IE", "Ireland", "🇮🇪", "+353"),
    IL("IL", "Israel", "🇮🇱", "+972"),
    IM("IM", "Isle of Man", "🇮🇲", "+44"),
    IN("IN", "India", "🇮🇳", "+91"),
    IO("IO", "British Indian Ocean Territory", "🇮🇴", "+246"),
    IQ("IQ", "Iraq", "🇮🇶", "+964"),
    IR("IR", "Iran", "🇮🇷", "+98"),
    IS("IS", "Iceland", "🇮🇸", "+354"),
    IT("IT", "Italy", "🇮🇹", "+39"),
    JE("JE", "Jersey", "🇯🇪", "+44"),
    JM("JM", "Jamaica", "🇯🇲", "+1"),
    JO("JO", "Jordan", "🇯🇴", "+962"),
    JP("JP", "Japan", "🇯🇵", "+81"),
    KE("KE", "Kenya", "🇰🇪", "+254"),
    KG("KG", "Kyrgyzstan", "🇰🇬", "+996"),
    KH("KH", "Cambodia", "🇰🇭", "+855"),
    KI("KI", "Kiribati", "🇰🇮", "+686"),
    KM("KM", "Comoros", "🇰🇲", "+269"),
    KN("KN", "Saint Kitts and Nevis", "🇰🇳", "+1"),
    KP("KP", "North Korea", "🇰🇵", "+850"),
    KR("KR", "South Korea", "🇰🇷", "+82"),
    KW("KW", "Kuwait", "🇰🇼", "+965"),
    KY("KY", "Cayman Islands", "🇰🇾", "+1"),
    KZ("KZ", "Kazakhstan", "🇰🇿", "+7"),
    LA("LA", "Laos", "🇱🇦", "+856"),
    LB("LB", "Lebanon", "🇱🇧", "+961"),
    LC("LC", "Saint Lucia", "🇱🇨", "+1"),
    LI("LI", "Liechtenstein", "🇱🇮", "+423"),
    LK("LK", "Sri Lanka", "🇱🇰", "+94"),
    LR("LR", "Liberia", "🇱🇷", "+231"),
    LS("LS", "Lesotho", "🇱🇸", "+266"),
    LT("LT", "Lithuania", "🇱🇹", "+370"),
    LU("LU", "Luxembourg", "🇱🇺", "+352"),
    LV("LV", "Latvia", "🇱🇻", "+371"),
    LY("LY", "Libya", "🇱🇾", "+218"),
    MA("MA", "Morocco", "🇲🇦", "+212"),
    MC("MC", "Monaco", "🇲🇨", "+377"),
    MD("MD", "Moldova", "🇲🇩", "+373"),
    ME("ME", "Montenegro", "🇲🇪", "+382"),
    MF("MF", "Saint Martin", "🇲🇫", "+590"),
    MG("MG", "Madagascar", "🇲🇬", "+261"),
    MH("MH", "Marshall Islands", "🇲🇭", "+692"),
    MK("MK", "North Macedonia", "🇲🇰", "+389"),
    ML("ML", "Mali", "🇲🇱", "+223"),
    MM("MM", "Myanmar", "🇲🇲", "+95"),
    MN("MN", "Mongolia", "🇲🇳", "+976"),
    MO("MO", "Macao", "🇲🇴", "+853"),
    MP("MP", "Northern Mariana Islands", "🇲🇵", "+1"),
    MQ("MQ", "Martinique", "🇲🇶", "+596"),
    MR("MR", "Mauritania", "🇲🇷", "+222"),
    MS("MS", "Montserrat", "🇲🇸", "+1"),
    MT("MT", "Malta", "🇲🇹", "+356"),
    MU("MU", "Mauritius", "🇲🇺", "+230"),
    MV("MV", "Maldives", "🇲🇻", "+960"),
    MW("MW", "Malawi", "🇲🇼", "+265"),
    MX("MX", "Mexico", "🇲🇽", "+52"),
    MY("MY", "Malaysia", "🇲🇾", "+60"),
    MZ("MZ", "Mozambique", "🇲🇿", "+258"),
    NA("NA", "Namibia", "🇳🇦", "+264"),
    NC("NC", "New Caledonia", "🇳🇨", "+687"),
    NE("NE", "Niger", "🇳🇪", "+227"),
    NF("NF", "Norfolk Island", "🇳🇫", "+672"),
    NG("NG", "Nigeria", "🇳🇬", "+234"),
    NI("NI", "Nicaragua", "🇳🇮", "+505"),
    NL("NL", "Netherlands", "🇳🇱", "+31"),
    NO("NO", "Norway", "🇳🇴", "+47"),
    NP("NP", "Nepal", "🇳🇵", "+977"),
    NR("NR", "Nauru", "🇳🇷", "+674"),
    NU("NU", "Niue", "🇳🇺", "+683"),
    NZ("NZ", "New Zealand", "🇳🇿", "+64"),
    OM("OM", "Oman", "🇴🇲", "+968"),
    PA("PA", "Panama", "🇵🇦", "+507"),
    PE("PE", "Peru", "🇵🇪", "+51"),
    PF("PF", "French Polynesia", "🇵🇫", "+689"),
    PG("PG", "Papua New Guinea", "🇵🇬", "+675"),
    PH("PH", "Philippines", "🇵🇭", "+63"),
    PK("PK", "Pakistan", "🇵🇰", "+92"),
    PL("PL", "Poland", "🇵🇱", "+48"),
    PM("PM", "Saint Pierre and Miquelon", "🇵🇲", "+508"),
    PN("PN", "Pitcairn", "🇵🇳", "+64"),
    PR("PR", "Puerto Rico", "🇵🇷", "+1"),
    PS("PS", "Palestine", "🇵🇸", "+970"),
    PT("PT", "Portugal", "🇵🇹", "+351"),
    PW("PW", "Palau", "🇵🇼", "+680"),
    PY("PY", "Paraguay", "🇵🇾", "+595"),
    QA("QA", "Qatar", "🇶🇦", "+974"),
    RE("RE", "Réunion", "🇷🇪", "+262"),
    RO("RO", "Romania", "🇷🇴", "+40"),
    RS("RS", "Serbia", "🇷🇸", "+381"),
    RU("RU", "Russia", "🇷🇺", "+7"),
    RW("RW", "Rwanda", "🇷🇼", "+250"),
    SA("SA", "Saudi Arabia", "🇸🇦", "+966"),
    SB("SB", "Solomon Islands", "🇸🇧", "+677"),
    SC("SC", "Seychelles", "🇸🇨", "+248"),
    SD("SD", "Sudan", "🇸🇩", "+249"),
    SE("SE", "Sweden", "🇸🇪", "+46"),
    SG("SG", "Singapore", "🇸🇬", "+65"),
    SH("SH", "Saint Helena", "🇸🇭", "+290"),
    SI("SI", "Slovenia", "🇸🇮", "+386"),
    SJ("SJ", "Svalbard and Jan Mayen", "🇸🇯", "+47"),
    SK("SK", "Slovakia", "🇸🇰", "+421"),
    SL("SL", "Sierra Leone", "🇸🇱", "+232"),
    SM("SM", "San Marino", "🇸🇲", "+378"),
    SN("SN", "Senegal", "🇸🇳", "+221"),
    SO("SO", "Somalia", "🇸🇴", "+252"),
    SR("SR", "Suriname", "🇸🇷", "+597"),
    SS("SS", "South Sudan", "🇸🇸", "+211"),
    ST("ST", "São Tomé and Príncipe", "🇸🇹", "+239"),
    SV("SV", "El Salvador", "🇸🇻", "+503"),
    SX("SX", "Sint Maarten", "🇸🇽", "+1"),
    SY("SY", "Syria", "🇸🇾", "+963"),
    SZ("SZ", "Eswatini", "🇸🇿", "+268"),
    TC("TC", "Turks and Caicos Islands", "🇹🇨", "+1"),
    TD("TD", "Chad", "🇹🇩", "+235"),
    TF("TF", "French Southern Territories", "🇹🇫", "+262"),
    TG("TG", "Togo", "🇹🇬", "+228"),
    TH("TH", "Thailand", "🇹🇭", "+66"),
    TJ("TJ", "Tajikistan", "🇹🇯", "+992"),
    TK("TK", "Tokelau", "🇹🇰", "+690"),
    TL("TL", "Timor-Leste", "🇹🇱", "+670"),
    TM("TM", "Turkmenistan", "🇹🇲", "+993"),
    TN("TN", "Tunisia", "🇹🇳", "+216"),
    TO("TO", "Tonga", "🇹🇴", "+676"),
    TR("TR", "Turkey", "🇹🇷", "+90"),
    TT("TT", "Trinidad and Tobago", "🇹🇹", "+1"),
    TV("TV", "Tuvalu", "🇹🇻", "+688"),
    TW("TW", "Taiwan", "🇹🇼", "+886"),
    TZ("TZ", "Tanzania", "🇹🇿", "+255"),
    UA("UA", "Ukraine", "🇺🇦", "+380"),
    UG("UG", "Uganda", "🇺🇬", "+256"),
    UM("UM", "United States Minor Outlying Islands", "🇺🇲", "+1"),
    US("US", "United States", "🇺🇸", "+1"),
    UY("UY", "Uruguay", "🇺🇾", "+598"),
    UZ("UZ", "Uzbekistan", "🇺🇿", "+998"),
    VA("VA", "Vatican City", "🇻🇦", "+39"),
    VC("VC", "Saint Vincent and the Grenadines", "🇻🇨", "+1"),
    VE("VE", "Venezuela", "🇻🇪", "+58"),
    VG("VG", "British Virgin Islands", "🇻🇬", "+1"),
    VI("VI", "U.S. Virgin Islands", "🇻🇮", "+1"),
    VN("VN", "Vietnam", "🇻🇳", "+84"),
    VU("VU", "Vanuatu", "🇻🇺", "+678"),
    WF("WF", "Wallis and Futuna", "🇼🇫", "+681"),
    WS("WS", "Samoa", "🇼🇸", "+685"),
    YE("YE", "Yemen", "🇾🇪", "+967"),
    YT("YT", "Mayotte", "🇾🇹", "+262"),
    ZA("ZA", "South Africa", "🇿🇦", "+27"),
    ZM("ZM", "Zambia", "🇿🇲", "+260"),
    ZW("ZW", "Zimbabwe", "🇿🇼", "+263");

    private final String code;
    private final String name;
    private final String flag;
    private final String phoneCode;

    Country(String code, String name, String flag, String phoneCode) {
        this.code = code;
        this.name = name;
        this.flag = flag;
        this.phoneCode = phoneCode;
    }

    public static Country getByCode(String code) {
        for (Country country : Country.values()) {
            if (country.getCode().equalsIgnoreCase(code)) {
                return country;
            }
        }
        return null;
    }

    public static Country getByPhoneCode(String phoneCode) {
        for (Country country : Country.values()) {
            if (country.getPhoneCode().equals(phoneCode)) {
                return country;
            }
        }
        return null;
    }

    public static Country getByName(String name) {
        for (Country country : Country.values()) {
            if (country.getName().equalsIgnoreCase(name)) {
                return country;
            }
        }
        return null;
    }

    public static List<Country> searchCountries(String query) {
        String searchTerm = query.toLowerCase();
        return Arrays.stream(Country.values())
                .filter(country ->
                        country.getName().toLowerCase().contains(searchTerm) ||
                                country.getCode().toLowerCase().contains(searchTerm) ||
                                country.getPhoneCode().contains(searchTerm)
                )
                .collect(Collectors.toList());
    }

    // For JSON serialization
    @JsonValue
    public Map<String, String> toJson() {
        Map<String, String> map = new HashMap<>();
        map.put("code", this.code);
        map.put("name", this.name);
        map.put("flag", this.flag);
        map.put("phoneCode", this.phoneCode);
        return map;
    }

    // For creating from JSON
    @JsonCreator
    public static Country fromJson(String code) {
        return getByCode(code);
    }

    @Override
    public String toString() {
        return String.format("Country{code='%s', name='%s', flag='%s', phoneCode='%s'}",
                code, name, flag, phoneCode);
    }
}