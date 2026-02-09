package regulatory_exams.models;

/**
 * Represents detailed salary breakdown for a regulatory body officer position.
 * Contains all allowances and deductions.
 * 
 * @author Documentation generated on 2026-01-28
 * @version 1.0
 */
public class SalaryBreakdown {
    private String regulatoryBody;
    private String grade;
    private double basicPay;
    private double dearnessAllowance;
    private double houseRentAllowance;
    private double gradeAllowance;
    private double specialAllowance;
    private double localAllowance;
    private double familyAllowance;
    private double otherAllowances;
    private double grossSalary;
    private double deductions;
    private double inHandSalary;
    private double annualCTC;

    public SalaryBreakdown(String regulatoryBody, String grade) {
        this.regulatoryBody = regulatoryBody;
        this.grade = grade;
    }

    // Builder pattern for easy construction
    public SalaryBreakdown withBasicPay(double basicPay) {
        this.basicPay = basicPay;
        return this;
    }

    public SalaryBreakdown withDA(double da) {
        this.dearnessAllowance = da;
        return this;
    }

    public SalaryBreakdown withHRA(double hra) {
        this.houseRentAllowance = hra;
        return this;
    }

    public SalaryBreakdown withGradeAllowance(double gradeAllowance) {
        this.gradeAllowance = gradeAllowance;
        return this;
    }

    public SalaryBreakdown withSpecialAllowance(double specialAllowance) {
        this.specialAllowance = specialAllowance;
        return this;
    }

    public SalaryBreakdown withLocalAllowance(double localAllowance) {
        this.localAllowance = localAllowance;
        return this;
    }

    public SalaryBreakdown withFamilyAllowance(double familyAllowance) {
        this.familyAllowance = familyAllowance;
        return this;
    }

    public SalaryBreakdown withOtherAllowances(double otherAllowances) {
        this.otherAllowances = otherAllowances;
        return this;
    }

    public SalaryBreakdown withGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
        return this;
    }

    public SalaryBreakdown withDeductions(double deductions) {
        this.deductions = deductions;
        return this;
    }

    public SalaryBreakdown withInHandSalary(double inHandSalary) {
        this.inHandSalary = inHandSalary;
        return this;
    }

    public SalaryBreakdown withAnnualCTC(double annualCTC) {
        this.annualCTC = annualCTC;
        return this;
    }

    // Getters
    public String getRegulatoryBody() {
        return regulatoryBody;
    }

    public String getGrade() {
        return grade;
    }

    public double getBasicPay() {
        return basicPay;
    }

    public double getDearnessAllowance() {
        return dearnessAllowance;
    }

    public double getHouseRentAllowance() {
        return houseRentAllowance;
    }

    public double getGradeAllowance() {
        return gradeAllowance;
    }

    public double getSpecialAllowance() {
        return specialAllowance;
    }

    public double getLocalAllowance() {
        return localAllowance;
    }

    public double getFamilyAllowance() {
        return familyAllowance;
    }

    public double getOtherAllowances() {
        return otherAllowances;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public double getDeductions() {
        return deductions;
    }

    public double getInHandSalary() {
        return inHandSalary;
    }

    public double getAnnualCTC() {
        return annualCTC;
    }

    @Override
    public String toString() {
        return String.format(
                """
                        ┌─────────────────────────────────────────────────────────────────┐
                        │ SALARY BREAKDOWN: %s %s
                        ├─────────────────────────────────────────────────────────────────┤
                        │ Component                          │ Amount (₹/month)           │
                        ├─────────────────────────────────────────────────────────────────┤
                        │ Basic Pay                          │ ₹%,.0f
                        │ Dearness Allowance (DA)            │ ₹%,.0f
                        │ House Rent Allowance (HRA)         │ ₹%,.0f
                        │ Grade Allowance                    │ ₹%,.0f
                        │ Special Allowance                  │ ₹%,.0f
                        │ Local Allowance                    │ ₹%,.0f
                        │ Family Allowance                   │ ₹%,.0f
                        │ Other Allowances                   │ ₹%,.0f
                        ├─────────────────────────────────────────────────────────────────┤
                        │ GROSS SALARY                       │ ₹%,.0f
                        │ Deductions (Tax, NPS, etc.)        │ ₹%,.0f
                        ├─────────────────────────────────────────────────────────────────┤
                        │ IN-HAND SALARY                     │ ₹%,.0f
                        │ ANNUAL CTC                         │ ₹%,.0f Lakhs
                        └─────────────────────────────────────────────────────────────────┘
                        """,
                regulatoryBody, grade,
                basicPay, dearnessAllowance, houseRentAllowance, gradeAllowance,
                specialAllowance, localAllowance, familyAllowance, otherAllowances,
                grossSalary, deductions, inHandSalary, annualCTC);
    }
}
