package seedu.expensela.logic.parser;

import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expensela.logic.commands.CommandTestUtil.*;
import static seedu.expensela.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.expensela.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.expensela.testutil.TypicalTransactions.AIRPODS;

import org.junit.jupiter.api.Test;

import seedu.expensela.logic.commands.AddCommand;
import seedu.expensela.model.transaction.*;
import seedu.expensela.testutil.TransactionBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Transaction expectedTransaction = new TransactionBuilder(AIRPODS).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, new AddCommand(expectedTransaction));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_PIZZA + NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, new AddCommand(expectedTransaction));

        // multiple amount - last amount accepted
        assertParseSuccess(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_PIZZA + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, new AddCommand(expectedTransaction));

        // multiple date - last date accepted
        assertParseSuccess(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_PIZZA + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, new AddCommand(expectedTransaction));

        // multiple remark - last remark accepted
        assertParseSuccess(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS + REMARK_DESC_PIZZA
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, new AddCommand(expectedTransaction));

        // multiple tags - all accepted
        Transaction expectedTransactionMultipleTags = new TransactionBuilder(AIRPODS)
                .build();
        assertParseSuccess(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS + REMARK_DESC_AIRPODS
                + CATEGORY_DESC_FOOD + CATEGORY_DESC_SHOPPING, new AddCommand(expectedTransactionMultipleTags));
    }

    /*
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Transaction expectedTransaction = new TransactionBuilder(PIZZA).build();
        assertParseSuccess(parser, NAME_DESC_PIZZA + AMOUNT_DESC_PIZZA + DATE_DESC_PIZZA + CATEGORY_DESC_FOOD,
                new AddCommand(expectedTransaction));
    } */

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, "add" + VALID_NAME_AIRPODS + AMOUNT_DESC_AIRPODS + REMARK_DESC_AIRPODS,
                expectedMessage);

        // missing amount prefix
        assertParseFailure(parser, "add" + NAME_DESC_AIRPODS + VALID_AMOUNT_AIRPODS + REMARK_DESC_AIRPODS,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, "add" + NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + VALID_REMARK_AIRPODS,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, "add" + VALID_NAME_AIRPODS + VALID_AMOUNT_AIRPODS + VALID_REMARK_AIRPODS,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Name.MESSAGE_CONSTRAINTS);

        // invalid amount
        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + INVALID_DATE_DESC
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING, Date.MESSAGE_CONSTRAINTS);

//        // invalid remark
//        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
//                + INVALID_REMARK_DESC + CATEGORY_DESC_SHOPPING, Remark.MESSAGE_CONSTRAINTS);

        //invalid category
        assertParseFailure(parser, NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, NAME_DESC_AIRPODS + INVALID_AMOUNT_DESC + DATE_DESC_AIRPODS
                + INVALID_REMARK_DESC + CATEGORY_DESC_SHOPPING, Amount.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_AIRPODS + AMOUNT_DESC_AIRPODS + DATE_DESC_AIRPODS
                + REMARK_DESC_AIRPODS + CATEGORY_DESC_SHOPPING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
