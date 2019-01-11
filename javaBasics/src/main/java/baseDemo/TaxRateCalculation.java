package baseDemo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2019税率计算
 */
public class TaxRateCalculation {

    public static void main(String[] args) {
        int BasePay = 30000;//工资
        int monthlyDeductions = 5000;//每月减除费用
        int specialDeduction = 4500;//专项扣除
        int specialAdditionalDeduction = 2000;//专项附加扣除
        List<AnnualTaxableIncome> annualTaxableIncomes = AnnualTaxableIncome.getTaxRateInit();
        Map<Integer, Double> temp = new HashMap<>();
        double total = 0;
        for (int i = 1; i < 13; i++) {
            //工资 = （基本工资 - 每月减除费用 - 专项扣除 - 专项附加扣除）
            Integer wages = i * (BasePay - monthlyDeductions - specialDeduction - specialAdditionalDeduction);
            for (AnnualTaxableIncome annualTaxableIncome : annualTaxableIncomes) {
                if (wages > annualTaxableIncome.getLowerLimit() && wages <= annualTaxableIncome.getUpperLimit()) {
                    //缴税 = 工资 * 税率 - 速算扣除数 - 上月缴税数
                    double taxPaymentLastMonth = 0;
                    if (i == 1){
                        taxPaymentLastMonth = 0;
                    }else {
                        int monthly = i;
                        while ((monthly - 1) > 0){
                            taxPaymentLastMonth = taxPaymentLastMonth + temp.get(--monthly);
                        }
                    }
                    double payTaxes = wages * annualTaxableIncome.getTaxRate() - annualTaxableIncome.getQuickCalculationDeduction() - taxPaymentLastMonth;
                    total = total + payTaxes;
                    temp.put(i, payTaxes);
                    System.out.println(i + "月份需要缴税为：[" + payTaxes + "]，" +
                            "缴税等级为：[" + annualTaxableIncome.getSeries() + "],计算公式为：" + i + " * (" + BasePay + " - " + monthlyDeductions + " - " +
                            specialDeduction + " - " + specialAdditionalDeduction + ") * " + annualTaxableIncome.getTaxRate() + " - " +
                            annualTaxableIncome.getQuickCalculationDeduction() + " - " + taxPaymentLastMonth);
                    break;
                }
            }
        }
        System.out.println(total);
    }


    //全年应纳税所得额
    static class AnnualTaxableIncome {
        //级数
        private String series;
        //下限
        private Integer lowerLimit;
        //上限
        private Integer UpperLimit;
        //税率
        private Double taxRate;
        //速算扣除数
        private Integer quickCalculationDeduction;

        AnnualTaxableIncome(String series, Integer lowerLimit, Integer upperLimit, Double taxRate, Integer quickCalculationDeduction) {
            this.series = series;
            this.lowerLimit = lowerLimit;
            UpperLimit = upperLimit;
            this.taxRate = taxRate;
            this.quickCalculationDeduction = quickCalculationDeduction;
        }

        public String getSeries() {
            return series;
        }

        public void setSeries(String series) {
            this.series = series;
        }

        public Integer getLowerLimit() {
            return lowerLimit;
        }

        public void setLowerLimit(Integer lowerLimit) {
            this.lowerLimit = lowerLimit;
        }

        public Integer getUpperLimit() {
            return UpperLimit;
        }

        public void setUpperLimit(Integer upperLimit) {
            UpperLimit = upperLimit;
        }

        public Double getTaxRate() {
            return taxRate;
        }

        public void setTaxRate(Double taxRate) {
            this.taxRate = taxRate;
        }

        public Integer getQuickCalculationDeduction() {
            return quickCalculationDeduction;
        }

        public void setQuickCalculationDeduction(Integer quickCalculationDeduction) {
            this.quickCalculationDeduction = quickCalculationDeduction;
        }

        public static List<AnnualTaxableIncome> getTaxRateInit() {
            AnnualTaxableIncome taxableIncome1 = new AnnualTaxableIncome("1", 0, 36000, 0.03, 0);
            AnnualTaxableIncome taxableIncome2 = new AnnualTaxableIncome("2", 36000, 144000, 0.1, 2520);
            AnnualTaxableIncome taxableIncome3 = new AnnualTaxableIncome("3", 144000, 300000, 0.2, 16920);
            AnnualTaxableIncome taxableIncome4 = new AnnualTaxableIncome("4", 300000, 420000, 0.25, 31920);
            AnnualTaxableIncome taxableIncome5 = new AnnualTaxableIncome("5", 420000, 660000, 0.3, 52920);
            AnnualTaxableIncome taxableIncome6 = new AnnualTaxableIncome("6", 660000, 960000, 0.35, 85920);
            AnnualTaxableIncome taxableIncome7 = new AnnualTaxableIncome("7", 960000, 99999999, 0.45, 181920);
            return Arrays.asList(taxableIncome1, taxableIncome2, taxableIncome3, taxableIncome4, taxableIncome5, taxableIncome6, taxableIncome7);
        }
    }


}


