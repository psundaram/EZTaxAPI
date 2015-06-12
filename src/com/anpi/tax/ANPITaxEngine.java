/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anpi.tax;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import eztaxconstants.ServiceType;
import eztaxconstants.TransactionType;

import billsoft.eztax.BusinessClass;
import billsoft.eztax.CustomerType;
import billsoft.eztax.EZTax;
import billsoft.eztax.EZTaxException;
import billsoft.eztax.EZTaxSession;
import billsoft.eztax.FilePaths;
import billsoft.eztax.ServiceClass;
import billsoft.eztax.TaxData;
import billsoft.eztax.Transaction;
import billsoft.eztax.ZipAddress;

/**
 *
 * @author rnarayanaswamy
 */
public class ANPITaxEngine {

    public static class Address {

        public String country;
        public String state;
        public String county;
        public String city;
        public String zip;
        public String zip4;

        public Address(String country, String state, String county, String city, String zip, String zip4) {
            this.country = country;
            this.state = state;
            this.county = county;
            this.city = city;
            this.zip = zip;
            this.zip4 = zip4;
        }
    }
    private String baseDirectory = "/anpi/billsoft/";
    private String logsDirectory = "/anpi/billsoft/logs/";
    private String dbDirectory = "/anpi/billsoft/db/";
    public static DecimalFormat df = new DecimalFormat("0.0000");
    static SimpleDateFormat uuidFormat;
    // Session will be created only once.
    private EZTaxSession __session;
    //A session ID will be created so that all log files will be created with the session id
    private String __sessionId;
    private Transaction __transaction;
    private Map __taxSummary;
    private CustomerInfo __customerInfo;

    // This will create a Tax Session
    public void init() {
        if (__session != null && __session.isOpen()) {
            __session.close();
        }

        uuidFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        __sessionId = uuidFormat.format(new Date());
        __session = initSession();
        __taxSummary = new HashMap();
    }

    private void clearTransaction() {
        if (__transaction != null) {
            __transaction.clear();
        }
        __transaction = null;
    }

    private Transaction newTransaction() {
        __transaction = null;
        getTransaction();
        initTransaction(__customerInfo);
        return __transaction;
    }

    private Transaction getTransaction() {
        if (__transaction != null) {
            return __transaction;
        }

        try {
            if (__session == null || !__session.isOpen()) {
                init();
            }
            __transaction = __session.createTransaction();
            return __transaction;

        } catch (EZTaxException ex) {
            System.out.println("An EZTaxException occurred while creating a transaction: " + ex.getMessage());
        }
        return null;
    }

    public void clear() {
        __transaction.clear();
        __session = null;
        if (__taxSummary != null) {
            __taxSummary.clear();
        }
    }

    static class CustomerInfo {

        String companyIdentifier;
        String custNumber;
        CustomerType customerType;
        long invoiceNumber;
        Address ba;
        Address ja;
        Date billFrom;
        Date billTo;

        public CustomerInfo(String companyIdentifier,
                String custNumber,
                CustomerType customerType,
                long invoiceNumber,
                Address ba,
                Address ja,Date billFrom, Date billTo) {
            this.companyIdentifier = companyIdentifier;
            this.custNumber = custNumber;
            this.invoiceNumber = invoiceNumber;
            this.customerType = customerType;
            this.ba = ba;
            this.ja = ja;
            this.billFrom = billFrom;
            this.billTo = billTo;
        }
    }

    public void setCustomerInfo(
            CustomerInfo info) {
        __customerInfo = info;
    }

    private void initTransaction(CustomerInfo info) {
        try {
            Transaction tran = getTransaction();
            tran.setBusinessClass(BusinessClass.CLEC);
            tran.setCustomerNumber(info.custNumber);
            tran.setCustomerType(CustomerType.BUSINESS);
            tran.setIncorporated(true);
            tran.setInvoiceNumber(info.invoiceNumber);
            tran.setLifeline(false);
            tran.setServiceClass(ServiceClass.PRIMARY_LOCAL); // how about wholesale?
            tran.setServiceLevelNumber(0);
            //Need to make provision for other individual tax exemptions
            //tran.setOptionalAlpha1("01234567890123456789");

            tran.setCompanyIdentifier(info.companyIdentifier);
            tran.setFacilitiesBased(false);
            tran.setFranchise(false);
            tran.setRegulated(false);

            ZipAddress bAddr = new ZipAddress(info.ba.country, info.ba.state, info.ba.county, info.ba.city, info.ba.zip, info.ba.zip4);
            ZipAddress jAddr = new ZipAddress(info.ja.country, info.ja.state, info.ja.county, info.ja.city, info.ja.zip, info.ja.zip4);

            long jCode = __session.zipToJCode(jAddr);
            tran.setBillToJCode(jCode);
            tran.setOriginationJCode(jCode);
            tran.setTerminationJCode(jCode);

            long pCode = __session.zipToPCode(bAddr);
            tran.setBillToPCode(pCode);
            tran.setOriginationPCode(pCode);
            tran.setTerminationPCode(pCode);
            tran.setSale(true);


        } catch (Exception ex) {
            System.out.println("An EZTaxException occurred while setCustomerInfo: " + ex.getMessage());
        }
    }

    private void setTaxCodes(Transaction tran, short taxTranType, short taxServiceType) {
        tran.setTransactionType(taxTranType);
        tran.setServiceType(taxServiceType);
    }

    public TaxData[] getAccessTax(short taxTranType, short taxServiceType, Date d, double charge) {
        try {
            Transaction tran = newTransaction();
            tran.setDate(d);
            tran.setCharge(charge);
            tran.setLines(0);
            tran.setLocations(0);
            tran.setMinutes(0.00);
            setTaxCodes(tran, taxTranType, taxServiceType);
            TaxData[] taxes = tran.calculateTaxes();
            return taxes;
        } catch (EZTaxException ex) {
            System.out.println("An EZTaxException occurred: " + ex.getMessage());
        }
        return null;
    }

    public TaxData[] getLinesTax(Date d, int lines) {
        try {
            Transaction tran = newTransaction();
            tran.setDate(d);
            tran.setCharge(0.00);
            tran.setLines(lines);
            tran.setLocations(0);
            tran.setMinutes(0.00);
            tran.setTransactionType(TransactionType.VOIP);
            tran.setServiceType(ServiceType.LINES);
            TaxData[] taxes = tran.calculateTaxes();
            return taxes;
        } catch (EZTaxException ex) {
            System.out.println("An EZTaxException occurred: " + ex.getMessage());
        }
        return null;
    }

    public TaxData[] getIntraStateUsage(short taxTranType, short taxServiceType, Date d, double usageMin, double usageAmount) {
        try {
            Transaction tran = newTransaction();
            tran.setDate(d);
            tran.setCharge(usageAmount);
            tran.setLines(0);
            tran.setLocations(0);
            tran.setMinutes(usageMin);
            setTaxCodes(tran, taxTranType, taxServiceType);
            TaxData[] taxes = tran.calculateTaxes();
            return taxes;
        } catch (EZTaxException ex) {
            System.out.println("An EZTaxException occurred: " + ex.getMessage());
        }
        return null;
    }

    public TaxData[] getInterStateUsage(short taxTranType, short taxServiceType, Date d, double usageMin, double usageAmount) {
        try {
            Transaction tran = newTransaction();
            tran.setDate(d);
            tran.setCharge(usageAmount);
            tran.setLines(0);
            tran.setLocations(0);
            tran.setMinutes(usageMin);
            setTaxCodes(tran, taxTranType, taxServiceType);
            TaxData[] taxes = tran.calculateTaxes();
            return taxes;
        } catch (EZTaxException ex) {
            System.out.println("An EZTaxException occurred: " + ex.getMessage());
        }
        return null;
    }

    public TaxData[] getInternationalUsage(short taxTranType, short taxServiceType, Date d, double usageMin, double usageAmount) {
        try {
            Transaction tran = newTransaction();
            tran.setDate(d);
            tran.setCharge(usageAmount);
            tran.setLines(0);
            tran.setLocations(0);
            tran.setMinutes(usageMin);
            setTaxCodes(tran, taxTranType, taxServiceType);
            TaxData[] taxes = tran.calculateTaxes();
            return taxes;
        } catch (EZTaxException ex) {
            System.out.println("An EZTaxException occurred: " + ex.getMessage());
        }
        return null;
    }

    public TaxData[] getVOIPInvoiceTax(Date d) {
        try {
            Transaction tran = newTransaction();
            tran.setDate(d);
            setTaxCodes(tran, TransactionType.VOIP, ServiceType.INVOICE);
            TaxData[] taxes = tran.calculateTaxes();
            return taxes;
        } catch (EZTaxException ex) {
            System.out.println("An EZTaxException occurred: " + ex.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        runScenario2();
    }

    private static void runScenario2() {
        ANPITaxEngine engine = new ANPITaxEngine();
        engine.init();
        CustomerInfo info = new CustomerInfo("Shelby Wagner Design",
                "RCID:100976",
                CustomerType.BUSINESS,
                9002,
                new ANPITaxEngine.Address("USA", "TX", "Dallas", "Dallas", "75219", "4510"),
                new ANPITaxEngine.Address("USA", "TX", "Dallas", "Dallas", "75219", "4510"),new Date(),new Date());

        engine.setCustomerInfo(info);

        Calendar cal = Calendar.getInstance();
        cal.set(2015, 2, 28);
        Date date = cal.getTime();

        engine.printTaxes("Access Tax", engine.getAccessTax((short) 19, (short) 6, date, 89.97), ANPITaxEngine.df);
        engine.printTaxes("Access Tax", engine.getAccessTax((short) 19, (short) 577, date, 14.99), ANPITaxEngine.df);
        engine.printTaxes("Lines Tax", engine.getLinesTax(date, 3), ANPITaxEngine.df);
        engine.printTaxes("Invoice Tax", engine.getVOIPInvoiceTax(date), df);

//        engine.printTaxes("Tollfree Tax", engine.getAccessTax((short) 19, (short) 30, date, 9.34), ANPITaxEngine.df);
//        engine.printTaxes("InterStateUsage Tax", engine.getInterStateUsage((short)19, (short)6, date, 100.00, 50.00), ANPITaxEngine.df);
//        engine.printTaxes("IntraStateUsage Tax", engine.getIntraStateUsage((short)19, (short)6, date, 100.00, 50.00), ANPITaxEngine.df);
//        engine.printTaxes("Outbound International", engine.getInternationalUsage((short) 19, (short) 6, date, 23841.1, 11100.6371), ANPITaxEngine.df);
//        engine.printTaxes("Outbound International Mobile", engine.getInternationalUsage((short) 19, (short) 6, date, 30898.7, 10242.1803), ANPITaxEngine.df);
//        engine.printTaxes("Outbound Intl NADP", engine.getInternationalUsage((short) 19, (short) 6, date, 2.1, 0.384), ANPITaxEngine.df);
//        engine.printTaxes("Inbound Non Toll Free", engine.getInternationalUsage((short) 19, (short) 6, date, 25, 0.00), ANPITaxEngine.df);
//        engine.printTaxes("Outbound Toll Free", engine.getInternationalUsage((short) 19, (short) 6, date, 11.8, 0.00), ANPITaxEngine.df);

        engine.printSummary();
    }

    private static void runScenario1() {
        ANPITaxEngine engine = new ANPITaxEngine();
        engine.init();
        CustomerInfo info = new CustomerInfo("Sports Home ID",
                "RCID:103257",
                CustomerType.BUSINESS,
                8002,
                new ANPITaxEngine.Address("USA", "NY", "Queens", "Springfield Gardens", "11413", "4001"),
                new ANPITaxEngine.Address("USA", "NY", "Queens", "Springfield Gardens", "11413", "4001"),new Date(),new Date());

        engine.setCustomerInfo(info);

        Calendar cal = Calendar.getInstance();
        cal.set(2015, 2, 28);
        Date date = cal.getTime();

        engine.printTaxes("Access Tax", engine.getAccessTax((short) 19, (short) 6, date, 123.63), ANPITaxEngine.df);
        engine.printTaxes("Lines Tax", engine.getLinesTax(date, 7), ANPITaxEngine.df);
        engine.printTaxes("Invoice Tax", engine.getVOIPInvoiceTax(date), df);

        engine.printTaxes("Tollfree Tax", engine.getAccessTax((short) 19, (short) 30, date, 9.34), ANPITaxEngine.df);
        //engine.printTaxes("InterStateUsage Tax", engine.getInterStateUsage((short)19, (short)6, date, 100.00, 50.00), ANPITaxEngine.df);
        //engine.printTaxes("IntraStateUsage Tax", engine.getIntraStateUsage((short)19, (short)6, date, 100.00, 50.00), ANPITaxEngine.df);
        engine.printTaxes("Outbound International", engine.getInternationalUsage((short) 19, (short) 6, date, 23841.1, 11100.6371), ANPITaxEngine.df);
        engine.printTaxes("Outbound International Mobile", engine.getInternationalUsage((short) 19, (short) 6, date, 30898.7, 10242.1803), ANPITaxEngine.df);
        engine.printTaxes("Outbound Intl NADP", engine.getInternationalUsage((short) 19, (short) 6, date, 2.1, 0.384), ANPITaxEngine.df);
        engine.printTaxes("Inbound Non Toll Free", engine.getInternationalUsage((short) 19, (short) 6, date, 25, 0.00), ANPITaxEngine.df);
        engine.printTaxes("Outbound Toll Free", engine.getInternationalUsage((short) 19, (short) 6, date, 11.8, 0.00), ANPITaxEngine.df);

        engine.printSummary();
    }

    public void printTaxes(String taxType, TaxData[] taxes, DecimalFormat df) {
        System.out.println("====================Begin Tax: " + taxType + "============================");
        if (taxes == null) {
            System.out.println("No taxes returned for the following transaction:" + taxType);
        } else {
            System.out.println("************* # of taxes: " + taxes.length);
            for (TaxData tax : taxes) {
                System.out.print("Tax: " + tax.getTaxLevel() + " - " + tax.getDescription());
                System.out.println("   Rate: " + df.format(tax.getRate())
                        + ",  Amount: " + df.format(tax.getTaxAmount())
                        + ",  Taxable Measure: " + df.format(tax.getTaxableMeasure()));
                addToTaxSummary(tax);
            }
            System.out.println("-------------------End Tax: " + taxType + "-------------------------------");
        }
    }

    private void printSummary() {
        if (__taxSummary == null || __taxSummary.isEmpty()) {
            System.out.println("No summary found...");
            return;
        }
        double totalTax = 0;
        System.out.println("************************* Tax Summary *************");
        Set keys = __taxSummary.keySet();
        String k;
        Double val;
        for (Object key : keys) {
            k = (String) key;
            val = (Double) __taxSummary.get(key);
            System.out.println("Desc:" + k + ",value:" + df.format(val));
            totalTax += val;
        }
        System.out.println("************************* Tax Summary *************" + totalTax);
    }

    private void addToTaxSummary(TaxData tax) {
        if (__taxSummary == null) {
            __taxSummary = new HashMap();
        }
        String key = tax.getTaxLevel() + "_" + tax.getDescription();
        Double val = (Double) __taxSummary.get(key);
        if (val == null) {
            val = new Double(0.00);
        }

        __taxSummary.put(key, new Double(val.doubleValue() + tax.getTaxAmount()));
    }

    private EZTaxSession initSession() {
        EZTaxSession session = null;
        FilePaths paths = getFilePaths();

        try {
            System.out.println("************************************************************");
            System.out.println("*                  EZTax Sample Program                    *");
            System.out.println("************************************************************");
            System.out.println();
            System.out.println("EZTax DLL version:      " + EZTax.getDllVersion());
            System.out.println("EZTax database version: " + EZTax.getDatabaseVersion(paths.getDataFile()));
            System.out.println();

            // Attempt to initialize an EZTax session
            session = new EZTaxSession(true, paths);
            System.out.println("Logname: " + session.getLogName());

            if (session == null) {
                System.out.println("INIT ERROR: session is null!");
            } else {
                System.out.println("EZTax Session initialized");
            }
        } catch (EZTaxException ex) {
            System.out.println("An EZTaxException occurred: " + ex.getMessage());
            EZTax.exit();
            return null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            EZTax.exit();
            return null;
        }

        return session;
    }

    /**
     * Returns a FilePaths object containing the paths to the EZTax database
     * files.
     *
     * @return FilePaths object.
     */
    private FilePaths getFilePaths() {
        // Create a FilePaths object
        FilePaths paths = new FilePaths();

        paths.setDataFile(dbDirectory + "EZTax.dat");
        paths.setIdxFile(dbDirectory + "EZTax.idx");
        paths.setDllFile(dbDirectory + "EZTax.dll");
        paths.setLogFile(logsDirectory + "EZTax" + "_" + __sessionId + ".log");
        paths.setNpaNxxFile(dbDirectory + "EZTax.npa");
        paths.setStatusFile(logsDirectory + "EZTax" + "_" + __sessionId + ".sta");
        paths.setTempFile(logsDirectory + "tmp77777.dat");
        paths.setLocationFile(dbDirectory + "EZDesc.dat");
        paths.setZipFile(dbDirectory + "EZZip.dat");
        paths.setCustomerKeyFile(logsDirectory + "cust_key");
        paths.setPCodeFile(dbDirectory + "EZTax.pcd");
        paths.setJCodeFile(dbDirectory + "EZTax.jtp");
        paths.setOverrideFile("");

        return paths;
    }
}
