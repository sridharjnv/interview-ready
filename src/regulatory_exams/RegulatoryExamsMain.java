package regulatory_exams;

import regulatory_exams.data.RegulatoryExamsData;
import regulatory_exams.models.RegulatoryBody;
import regulatory_exams.models.SalaryBreakdown;

import java.util.List;

/**
 * ╔═══════════════════════════════════════════════════════════════════════════════════════╗
 * ║ REGULATORY BODY EXAMS - INDIA (2025-2026) ║
 * ║ Grade A & Grade B Officer Salaries ║
 * ╠═══════════════════════════════════════════════════════════════════════════════════════╣
 * ║ This documentation covers all major regulatory body exams in India
 * including: ║
 * ║ - RBI (Reserve Bank of India) Grade A & B ║
 * ║ - SEBI (Securities and Exchange Board of India) ║
 * ║ - NABARD (National Bank for Agriculture and Rural Development) ║
 * ║ - SIDBI (Small Industries Development Bank of India) ║
 * ║ - IRDAI (Insurance Regulatory and Development Authority of India) ║
 * ║ - PFRDA (Pension Fund Regulatory and Development Authority) ║
 * ║ - IBBI (Insolvency and Bankruptcy Board of India) ║
 * ║ - NHB (National Housing Bank) ║
 * ║ - EXIM Bank (Export-Import Bank of India) ║
 * ║ - FSSAI (Food Safety and Standards Authority of India) ║
 * ╠═══════════════════════════════════════════════════════════════════════════════════════╣
 * ║ Data Sources: ║
 * ║ - Official recruitment notifications from respective organizations ║
 * ║ - rbi.org.in, sebi.gov.in, nabard.org, irdai.gov.in, pfrda.org.in ║
 * ║ - testbook.com, oliveboard.in, careerpower.in, edutap.in, ixambee.com ║
 * ╠═══════════════════════════════════════════════════════════════════════════════════════╣
 * ║ Last Updated: January 28, 2026 ║
 * ╚═══════════════════════════════════════════════════════════════════════════════════════╝
 *
 * @author Sridhar
 * @version 1.0
 */
public class RegulatoryExamsMain {

    public static void main(String[] args) {
        System.out.println("""

                ╔═══════════════════════════════════════════════════════════════════════════════════════╗
                ║                    REGULATORY BODY EXAMS - INDIA (2025-2026)                          ║
                ║                           Grade A & Grade B Officer Salaries                          ║
                ╚═══════════════════════════════════════════════════════════════════════════════════════╝
                """);

        // Display all regulatory bodies
        displayAllRegulatoryBodies();

        // Display salary comparison
        displaySalaryComparison();

        // Display IT stream exams
        displayITStreamExams();

        // Display detailed salary breakdowns
        displayDetailedSalaryBreakdowns();
    }

    private static void displayAllRegulatoryBodies() {
        System.out.println("\n" + "═".repeat(80));
        System.out.println("                    ALL REGULATORY BODY EXAMS");
        System.out.println("═".repeat(80));

        List<RegulatoryBody> bodies = RegulatoryExamsData.getAllRegulatoryBodies();
        for (RegulatoryBody body : bodies) {
            System.out.println(body);
        }
    }

    private static void displaySalaryComparison() {
        System.out.println("\n" + "═".repeat(80));
        System.out.println("                    SALARY COMPARISON (CTC - Highest to Lowest)");
        System.out.println("═".repeat(80));

        System.out.println("""
                ┌──────┬─────────────────────┬────────────────┬─────────────────────┬──────────────────┐
                │ Rank │ Exam                │ Basic Pay      │ In-Hand Salary      │ Annual CTC       │
                ├──────┼─────────────────────┼────────────────┼─────────────────────┼──────────────────┤""");

        List<RegulatoryBody> sortedBodies = RegulatoryExamsData.getSortedByCTC();
        int rank = 1;
        for (RegulatoryBody body : sortedBodies) {
            String emoji = switch (rank) {
                case 1 -> "🥇";
                case 2 -> "🥈";
                case 3 -> "🥉";
                default -> String.valueOf(rank);
            };
            System.out.printf("│ %-4s │ %-19s │ ₹%,-12.0f │ ₹%,-8.0f - ₹%,-8.0f │ ₹%.0f - %.0f Lakhs │%n",
                    emoji,
                    body.getExamName(),
                    body.getBasicPay(),
                    body.getInHandSalaryMin(),
                    body.getInHandSalaryMax(),
                    body.getAnnualCTCMin(),
                    body.getAnnualCTCMax());
            rank++;
        }
        System.out.println("└──────┴─────────────────────┴────────────────┴─────────────────────┴──────────────────┘");
    }

    private static void displayITStreamExams() {
        System.out.println("\n" + "═".repeat(80));
        System.out.println("                    EXAMS WITH IT STREAM");
        System.out.println("═".repeat(80));

        List<RegulatoryBody> itExams = RegulatoryExamsData.getITStreamExams();

        System.out.println("""
                ┌─────────────────────┬────────────────┬─────────────────────────────────────────────────┐
                │ Exam                │ Annual CTC     │ IT Eligibility                                  │
                ├─────────────────────┼────────────────┼─────────────────────────────────────────────────┤""");

        for (RegulatoryBody exam : itExams) {
            String eligibility = exam.getItStreamEligibility();
            if (eligibility.length() > 47) {
                eligibility = eligibility.substring(0, 44) + "...";
            }
            System.out.printf("│ %-19s │ ₹%.0f-%.0f Lakhs  │ %-47s │%n",
                    exam.getExamName(),
                    exam.getAnnualCTCMin(),
                    exam.getAnnualCTCMax(),
                    eligibility);
        }
        System.out
                .println("└─────────────────────┴────────────────┴─────────────────────────────────────────────────┘");

        System.out.println("""

                📌 KEY OBSERVATIONS FOR IT PROFESSIONALS:
                ─────────────────────────────────────────
                1. RBI Grade B DSIM is the highest-paying IT-related regulatory exam (~₹32-42 Lakhs CTC)
                2. SEBI, NABARD, IRDAI, and RBI Grade A all have dedicated IT streams
                3. B.E./B.Tech or MCA is typically required for IT streams
                4. PFRDA, IBBI, NHB, EXIM Bank do NOT have separate IT streams
                5. IT stream syllabus typically covers: DBMS, OS, Networks, Data Structures, Cybersecurity
                6. For RBI Grade B DSIM, focus shifts to Data Science, ML, AI, Big Data, Statistics
                """);
    }

    private static void displayDetailedSalaryBreakdowns() {
        System.out.println("\n" + "═".repeat(80));
        System.out.println("                    DETAILED SALARY BREAKDOWNS");
        System.out.println("═".repeat(80));

        // RBI Grade B
        SalaryBreakdown rbiBreakdown = RegulatoryExamsData.getRBIGradeBSalaryBreakdown();
        System.out.println(rbiBreakdown);

        // IRDAI Grade A
        SalaryBreakdown irdaiBreakdown = RegulatoryExamsData.getIRDAIGradeASalaryBreakdown();
        System.out.println(irdaiBreakdown);
    }
}
