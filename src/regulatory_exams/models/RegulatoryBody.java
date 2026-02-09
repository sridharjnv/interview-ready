package regulatory_exams.models;

/**
 * Represents a Regulatory Body in India that conducts Grade A/B officer
 * recruitment exams.
 * 
 * @author Documentation generated on 2026-01-28
 * @version 1.0
 */
public class RegulatoryBody {
    private String name;
    private String fullName;
    private String examName;
    private String designation;
    private String grade;
    private double basicPay;
    private double grossSalary;
    private double inHandSalaryMin;
    private double inHandSalaryMax;
    private double annualCTCMin;
    private double annualCTCMax;
    private String payScale;
    private boolean hasITStream;
    private String itStreamEligibility;
    private String examFrequency;

    public RegulatoryBody(String name, String fullName, String examName, String designation, String grade) {
        this.name = name;
        this.fullName = fullName;
        this.examName = examName;
        this.designation = designation;
        this.grade = grade;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getBasicPay() {
        return basicPay;
    }

    public void setBasicPay(double basicPay) {
        this.basicPay = basicPay;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public double getInHandSalaryMin() {
        return inHandSalaryMin;
    }

    public void setInHandSalaryMin(double inHandSalaryMin) {
        this.inHandSalaryMin = inHandSalaryMin;
    }

    public double getInHandSalaryMax() {
        return inHandSalaryMax;
    }

    public void setInHandSalaryMax(double inHandSalaryMax) {
        this.inHandSalaryMax = inHandSalaryMax;
    }

    public double getAnnualCTCMin() {
        return annualCTCMin;
    }

    public void setAnnualCTCMin(double annualCTCMin) {
        this.annualCTCMin = annualCTCMin;
    }

    public double getAnnualCTCMax() {
        return annualCTCMax;
    }

    public void setAnnualCTCMax(double annualCTCMax) {
        this.annualCTCMax = annualCTCMax;
    }

    public String getPayScale() {
        return payScale;
    }

    public void setPayScale(String payScale) {
        this.payScale = payScale;
    }

    public boolean hasITStream() {
        return hasITStream;
    }

    public void setHasITStream(boolean hasITStream) {
        this.hasITStream = hasITStream;
    }

    public String getItStreamEligibility() {
        return itStreamEligibility;
    }

    public void setItStreamEligibility(String itStreamEligibility) {
        this.itStreamEligibility = itStreamEligibility;
    }

    public String getExamFrequency() {
        return examFrequency;
    }

    public void setExamFrequency(String examFrequency) {
        this.examFrequency = examFrequency;
    }

    @Override
    public String toString() {
        return String.format(
                """
                        ╔══════════════════════════════════════════════════════════════════════╗
                        ║ %s (%s)
                        ╠══════════════════════════════════════════════════════════════════════╣
                        ║ Full Name      : %s
                        ║ Exam Name      : %s
                        ║ Designation    : %s
                        ║ Grade          : %s
                        ║ Basic Pay      : ₹%.0f/month
                        ║ Gross Salary   : ₹%.0f/month
                        ║ In-Hand Salary : ₹%.0f - ₹%.0f/month
                        ║ Annual CTC     : ₹%.0f - ₹%.0f Lakhs
                        ║ Pay Scale      : %s
                        ║ IT Stream      : %s
                        ║ Exam Frequency : %s
                        ╚══════════════════════════════════════════════════════════════════════╝
                        """,
                name, grade, fullName, examName, designation, grade,
                basicPay, grossSalary, inHandSalaryMin, inHandSalaryMax,
                annualCTCMin, annualCTCMax, payScale,
                hasITStream ? "Yes - " + itStreamEligibility : "No",
                examFrequency);
    }
}
