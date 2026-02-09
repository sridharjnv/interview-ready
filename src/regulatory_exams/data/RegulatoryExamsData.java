package regulatory_exams.data;

import regulatory_exams.models.RegulatoryBody;
import regulatory_exams.models.SalaryBreakdown;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains all data about regulatory body exams in India.
 * Data sourced from official notifications and verified sources as of January
 * 2026.
 * 
 * Sources:
 * - RBI: rbi.org.in official notifications
 * - SEBI: sebi.gov.in official notifications
 * - NABARD: nabard.org official notifications
 * - IRDAI: irdai.gov.in official notifications
 * - PFRDA: pfrda.org.in official notifications
 * - SIDBI: sidbi.in official notifications
 * - IBBI: ibbi.gov.in official notifications
 * 
 * @author Documentation generated on 2026-01-28
 * @version 1.0
 */
public class RegulatoryExamsData {

    /**
     * Returns list of all Grade A/B regulatory body exams with salary details.
     * Data updated as of January 2026.
     */
    public static List<RegulatoryBody> getAllRegulatoryBodies() {
        List<RegulatoryBody> bodies = new ArrayList<>();

        // ═══════════════════════════════════════════════════════════════════════
        // 1. RBI GRADE B - HIGHEST PAYING REGULATORY EXAM
        // ═══════════════════════════════════════════════════════════════════════
        RegulatoryBody rbiGradeB = new RegulatoryBody(
                "RBI",
                "Reserve Bank of India",
                "RBI Grade B",
                "Manager",
                "Grade B");
        rbiGradeB.setBasicPay(78450);
        rbiGradeB.setGrossSalary(150374);
        rbiGradeB.setInHandSalaryMin(108000);
        rbiGradeB.setInHandSalaryMax(118820);
        rbiGradeB.setAnnualCTCMin(32);
        rbiGradeB.setAnnualCTCMax(42);
        rbiGradeB.setPayScale("₹78,450 – 4050(9) – 1,14,900 – EB – 4050(2) – 1,23,000 – 4650(4) – 1,41,600 (16 years)");
        rbiGradeB.setHasITStream(true);
        rbiGradeB.setItStreamEligibility(
                "DSIM Stream - Master's in Statistics/Data Science/AI/ML (55%) OR 4-year Bachelor's (60%)");
        rbiGradeB.setExamFrequency("Annual");
        bodies.add(rbiGradeB);

        // ═══════════════════════════════════════════════════════════════════════
        // 2. RBI GRADE A
        // ═══════════════════════════════════════════════════════════════════════
        RegulatoryBody rbiGradeA = new RegulatoryBody(
                "RBI",
                "Reserve Bank of India",
                "RBI Grade A",
                "Assistant Manager",
                "Grade A");
        rbiGradeA.setBasicPay(62500);
        rbiGradeA.setGrossSalary(122692);
        rbiGradeA.setInHandSalaryMin(105000);
        rbiGradeA.setInHandSalaryMax(112000);
        rbiGradeA.setAnnualCTCMin(24);
        rbiGradeA.setAnnualCTCMax(24);
        rbiGradeA.setPayScale(
                "₹62,500 – 3600(4) – 76,900 – 4050(7) – 1,05,250 – EB – 4050(4) – 1,21,450 – 4650(1) – 1,26,100 (17 years)");
        rbiGradeA.setHasITStream(true);
        rbiGradeA.setItStreamEligibility("B.E./B.Tech/M.Tech in CS/IT/Electronics OR MCA");
        rbiGradeA.setExamFrequency("Annual");
        bodies.add(rbiGradeA);

        // ═══════════════════════════════════════════════════════════════════════
        // 3. SEBI GRADE A
        // ═══════════════════════════════════════════════════════════════════════
        RegulatoryBody sebiGradeA = new RegulatoryBody(
                "SEBI",
                "Securities and Exchange Board of India",
                "SEBI Grade A",
                "Assistant Manager",
                "Grade A");
        sebiGradeA.setBasicPay(62500);
        sebiGradeA.setGrossSalary(184000); // Without accommodation in Mumbai
        sebiGradeA.setInHandSalaryMin(110000);
        sebiGradeA.setInHandSalaryMax(140000);
        sebiGradeA.setAnnualCTCMin(22);
        sebiGradeA.setAnnualCTCMax(24);
        sebiGradeA.setPayScale(
                "₹62,500 – 3600(4) – 76,900 – 4050(7) – 1,05,250 – EB – 4050(4) – 1,21,450 – 4650(1) – 1,26,100 (17 years)");
        sebiGradeA.setHasITStream(true);
        sebiGradeA.setItStreamEligibility("B.E./B.Tech (Any Branch) OR Bachelor's + PG in CS/IT/Computer Applications");
        sebiGradeA.setExamFrequency("Annual");
        bodies.add(sebiGradeA);

        // ═══════════════════════════════════════════════════════════════════════
        // 4. IRDAI GRADE A
        // ═══════════════════════════════════════════════════════════════════════
        RegulatoryBody irdaiGradeA = new RegulatoryBody(
                "IRDAI",
                "Insurance Regulatory and Development Authority of India",
                "IRDAI Grade A",
                "Assistant Manager",
                "Grade A");
        irdaiGradeA.setBasicPay(44500);
        irdaiGradeA.setGrossSalary(146000);
        irdaiGradeA.setInHandSalaryMin(98289);
        irdaiGradeA.setInHandSalaryMax(122000);
        irdaiGradeA.setAnnualCTCMin(25);
        irdaiGradeA.setAnnualCTCMax(25);
        irdaiGradeA.setPayScale(
                "₹44,500 – 2500(4) – 54,500 – 2850(7) – 74,450 – EB – 2850(4) – 85,850 – 3300(1) – 89,150 (17 years)");
        irdaiGradeA.setHasITStream(true);
        irdaiGradeA.setItStreamEligibility(
                "B.E./B.Tech (60%) in EE/ECE/IT/CS OR MCA OR Graduation + 2-year PG in Computers/IT");
        irdaiGradeA.setExamFrequency("Irregular (1-2 years)");
        bodies.add(irdaiGradeA);

        // ═══════════════════════════════════════════════════════════════════════
        // 5. NABARD GRADE A
        // ═══════════════════════════════════════════════════════════════════════
        RegulatoryBody nabardGradeA = new RegulatoryBody(
                "NABARD",
                "National Bank for Agriculture and Rural Development",
                "NABARD Grade A",
                "Assistant Manager",
                "Grade A");
        nabardGradeA.setBasicPay(44500);
        nabardGradeA.setGrossSalary(100000);
        nabardGradeA.setInHandSalaryMin(92000);
        nabardGradeA.setInHandSalaryMax(98000);
        nabardGradeA.setAnnualCTCMin(16);
        nabardGradeA.setAnnualCTCMax(18);
        nabardGradeA.setPayScale(
                "₹44,500 – 2500(4) – 54,500 – 2850(7) – 74,450 – EB – 2850(4) – 85,850 – 3300(1) – 89,150 (17 years)");
        nabardGradeA.setHasITStream(true);
        nabardGradeA
                .setItStreamEligibility("4-year B.E./B.Tech (60%) OR PG (55%) OR Graduation (60%) + DOEACC 'B' Level");
        nabardGradeA.setExamFrequency("Annual");
        bodies.add(nabardGradeA);

        // ═══════════════════════════════════════════════════════════════════════
        // 6. SIDBI GRADE A
        // ═══════════════════════════════════════════════════════════════════════
        RegulatoryBody sidbiGradeA = new RegulatoryBody(
                "SIDBI",
                "Small Industries Development Bank of India",
                "SIDBI Grade A",
                "Assistant Manager",
                "Grade A");
        sidbiGradeA.setBasicPay(44500);
        sidbiGradeA.setGrossSalary(100000);
        sidbiGradeA.setInHandSalaryMin(81000);
        sidbiGradeA.setInHandSalaryMax(90000);
        sidbiGradeA.setAnnualCTCMin(19);
        sidbiGradeA.setAnnualCTCMax(21);
        sidbiGradeA.setPayScale(
                "₹44,500 – 2500(4) – 54,500 – 2850(7) – 74,450 – EB – 2850(4) – 85,850 – 3300(1) – 89,150 (17 years)");
        sidbiGradeA.setHasITStream(false);
        sidbiGradeA.setItStreamEligibility("General stream covers Engineering/IT candidates");
        sidbiGradeA.setExamFrequency("Irregular");
        bodies.add(sidbiGradeA);

        // ═══════════════════════════════════════════════════════════════════════
        // 7. PFRDA GRADE A
        // ═══════════════════════════════════════════════════════════════════════
        RegulatoryBody pfrdaGradeA = new RegulatoryBody(
                "PFRDA",
                "Pension Fund Regulatory and Development Authority",
                "PFRDA Grade A",
                "Assistant Manager",
                "Grade A");
        pfrdaGradeA.setBasicPay(44500);
        pfrdaGradeA.setGrossSalary(157000);
        pfrdaGradeA.setInHandSalaryMin(90000);
        pfrdaGradeA.setInHandSalaryMax(100000);
        pfrdaGradeA.setAnnualCTCMin(20);
        pfrdaGradeA.setAnnualCTCMax(22);
        pfrdaGradeA.setPayScale(
                "₹44,500 – 2500(4) – 54,500 – 2850(7) – 74,450 – EB – 2850(4) – 85,850 – 3300(1) – 89,150 (17 years)");
        pfrdaGradeA.setHasITStream(false);
        pfrdaGradeA.setItStreamEligibility("N/A - General Stream Only");
        pfrdaGradeA.setExamFrequency("Irregular (2-3 years)");
        bodies.add(pfrdaGradeA);

        // ═══════════════════════════════════════════════════════════════════════
        // 8. IBBI GRADE A
        // ═══════════════════════════════════════════════════════════════════════
        RegulatoryBody ibbiGradeA = new RegulatoryBody(
                "IBBI",
                "Insolvency and Bankruptcy Board of India",
                "IBBI Grade A",
                "Assistant Manager",
                "Grade A");
        ibbiGradeA.setBasicPay(44500);
        ibbiGradeA.setGrossSalary(105000);
        ibbiGradeA.setInHandSalaryMin(85000);
        ibbiGradeA.setInHandSalaryMax(90000);
        ibbiGradeA.setAnnualCTCMin(18);
        ibbiGradeA.setAnnualCTCMax(20);
        ibbiGradeA.setPayScale(
                "₹44,500 – 2500(4) – 54,500 – 2850(7) – 74,450 – EB – 2850(4) – 85,850 – 3300(1) – 89,150 (17 years)");
        ibbiGradeA.setHasITStream(false);
        ibbiGradeA.setItStreamEligibility("N/A - General Stream Only");
        ibbiGradeA.setExamFrequency("Irregular");
        bodies.add(ibbiGradeA);

        // ═══════════════════════════════════════════════════════════════════════
        // 9. NHB GRADE A
        // ═══════════════════════════════════════════════════════════════════════
        RegulatoryBody nhbGradeA = new RegulatoryBody(
                "NHB",
                "National Housing Bank",
                "NHB Grade A",
                "Assistant Manager",
                "Grade A");
        nhbGradeA.setBasicPay(44500);
        nhbGradeA.setGrossSalary(100000);
        nhbGradeA.setInHandSalaryMin(85000);
        nhbGradeA.setInHandSalaryMax(90000);
        nhbGradeA.setAnnualCTCMin(16);
        nhbGradeA.setAnnualCTCMax(18);
        nhbGradeA.setPayScale(
                "₹44,500 – 2500(4) – 54,500 – 2850(7) – 74,450 – EB – 2850(4) – 85,850 – 3300(1) – 89,150 (17 years)");
        nhbGradeA.setHasITStream(false);
        nhbGradeA.setItStreamEligibility("N/A - General Stream Only");
        nhbGradeA.setExamFrequency("Irregular");
        bodies.add(nhbGradeA);

        // ═══════════════════════════════════════════════════════════════════════
        // 10. EXIM BANK MT
        // ═══════════════════════════════════════════════════════════════════════
        RegulatoryBody eximBank = new RegulatoryBody(
                "EXIM Bank",
                "Export-Import Bank of India",
                "EXIM Bank MT",
                "Management Trainee",
                "Entry Level");
        eximBank.setBasicPay(44500);
        eximBank.setGrossSalary(95000);
        eximBank.setInHandSalaryMin(80000);
        eximBank.setInHandSalaryMax(85000);
        eximBank.setAnnualCTCMin(15);
        eximBank.setAnnualCTCMax(17);
        eximBank.setPayScale("Similar to NABARD/SIDBI Grade A structure");
        eximBank.setHasITStream(false);
        eximBank.setItStreamEligibility("N/A - General Stream Only");
        eximBank.setExamFrequency("Irregular");
        bodies.add(eximBank);

        // ═══════════════════════════════════════════════════════════════════════
        // 11. FSSAI
        // ═══════════════════════════════════════════════════════════════════════
        RegulatoryBody fssai = new RegulatoryBody(
                "FSSAI",
                "Food Safety and Standards Authority of India",
                "FSSAI Technical Officer",
                "Technical Officer/CFSO",
                "Group A");
        fssai.setBasicPay(44900);
        fssai.setGrossSalary(75000);
        fssai.setInHandSalaryMin(60000);
        fssai.setInHandSalaryMax(68000);
        fssai.setAnnualCTCMin(5.2);
        fssai.setAnnualCTCMax(5.9);
        fssai.setPayScale("Pay Level 7 (7th CPC)");
        fssai.setHasITStream(false);
        fssai.setItStreamEligibility("N/A - Technical/Food Science streams only");
        fssai.setExamFrequency("Irregular");
        bodies.add(fssai);

        return bodies;
    }

    /**
     * Returns detailed salary breakdown for RBI Grade B Officer.
     * Source: Official RBI Recruitment Notification 2025
     */
    public static SalaryBreakdown getRBIGradeBSalaryBreakdown() {
        return new SalaryBreakdown("RBI", "Grade B")
                .withBasicPay(78450)
                .withDA(45501) // 58% of basic pay
                .withHRA(11768) // 15% of basic if not availing quarters
                .withGradeAllowance(7950)
                .withSpecialAllowance(15000)
                .withLocalAllowance(3660)
                .withFamilyAllowance(0)
                .withOtherAllowances(4225) // CVPS, meal, prerequisite allowances
                .withGrossSalary(150374)
                .withDeductions(35000) // Approximate NPS, tax, etc.
                .withInHandSalary(115000)
                .withAnnualCTC(35);
    }

    /**
     * Returns detailed salary breakdown for IRDAI Grade A Officer.
     * Source: Official IRDAI Recruitment Notification 2025
     */
    public static SalaryBreakdown getIRDAIGradeASalaryBreakdown() {
        return new SalaryBreakdown("IRDAI", "Grade A")
                .withBasicPay(44500)
                .withDA(25810) // Variable as per central DA rates
                .withHRA(17800) // Varies by city
                .withGradeAllowance(8900)
                .withSpecialAllowance(12000)
                .withLocalAllowance(5500)
                .withFamilyAllowance(3500)
                .withOtherAllowances(27990) // Qualification allowance, CCA, etc.
                .withGrossSalary(146000)
                .withDeductions(24000)
                .withInHandSalary(122000)
                .withAnnualCTC(25);
    }

    /**
     * Returns list of regulatory bodies with IT streams only.
     */
    public static List<RegulatoryBody> getITStreamExams() {
        return getAllRegulatoryBodies().stream()
                .filter(RegulatoryBody::hasITStream)
                .toList();
    }

    /**
     * Returns regulatory bodies sorted by Annual CTC (highest to lowest).
     */
    public static List<RegulatoryBody> getSortedByCTC() {
        return getAllRegulatoryBodies().stream()
                .sorted((a, b) -> Double.compare(b.getAnnualCTCMax(), a.getAnnualCTCMax()))
                .toList();
    }
}
