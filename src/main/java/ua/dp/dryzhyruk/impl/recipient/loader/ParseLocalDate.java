package ua.dp.dryzhyruk.impl.recipient.loader;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ParseLocalDate extends CellProcessorAdaptor {

    public ParseLocalDate() {
        super();
    }

    public ParseLocalDate(CellProcessor next) {
        super(next);
    }

    @Override
    public Object execute(Object value, CsvContext context) {
        validateInputNotNull(value, context);

        DateTimeFormatter[] dateFormats = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy") };

        LocalDate date = null;
        for (DateTimeFormatter dtf : dateFormats) {
            try {
                date = LocalDate.parse(value.toString(), dtf);
                break;
            } catch (Exception e) {
                // was not able to be parsed with this format, do nothing
            }
        }

        if (date == null)
            throw new SuperCsvCellProcessorException("Date could not be parsed", context, this);

        return date;
    }
}
