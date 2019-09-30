package com.ramitsuri.calorietracker.utils;

import com.google.api.services.sheets.v4.model.AppendCellsRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BooleanCondition;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.ConditionValue;
import com.google.api.services.sheets.v4.model.DataValidationRule;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.RowData;
import com.ramitsuri.calorietracker.entities.TrackedItem;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class SheetRequestHelper {

    public static BatchUpdateSpreadsheetRequest getUpdateRequestBody(
            List<TrackedItem> trackedItemList,
            String sheetId) {
        BatchUpdateSpreadsheetRequest requestBody = new BatchUpdateSpreadsheetRequest();
        List<Request> requests = new ArrayList<>();
        Request request = new Request();
        AppendCellsRequest appendCellsRequest = new AppendCellsRequest();
        appendCellsRequest.setFields("*");
        int sheetIdInt;
        try {
            sheetIdInt = Integer.parseInt(sheetId);
        } catch (NumberFormatException ex) {
            Timber.e("Failed to convert sheet id to string");
            return null;
        }
        appendCellsRequest.setSheetId(sheetIdInt);

        List<RowData> rowDataList = new ArrayList<>();
        for (TrackedItem trackedItem : trackedItemList) {
            RowData rowData = new RowData();
            List<CellData> cellDataList = new ArrayList<>();
            CellData cellData;

            // Day
            cellData = new CellData();
            cellData.setUserEnteredValue(new ExtendedValue().setNumberValue(
                    (double)Integer.parseInt(DateHelper.getJustDay(trackedItem.getDate()))));
            cellData.setDataValidation(new DataValidationRule()
                    .setCondition(new BooleanCondition().setType("ONE_OF_LIST")
                            .setValues(getDayConditionValues()))
                    .setStrict(true)
                    .setShowCustomUi(true));
            cellDataList.add(cellData);

            // Item Name
            cellData = new CellData();
            cellData.setUserEnteredValue(
                    new ExtendedValue().setStringValue(trackedItem.getItemName()));
            cellDataList.add(cellData);

            // Portion
            cellData = new CellData();
            cellData.setUserEnteredValue(new ExtendedValue()
                    .setNumberValue(trackedItem.getPortionSize().doubleValue()));
            cellDataList.add(cellData);

            rowData.setValues(cellDataList);
            rowDataList.add(rowData);
        }
        appendCellsRequest.setRows(rowDataList);
        request.setAppendCells(appendCellsRequest);
        requests.add(request);
        requestBody.setRequests(requests);
        return requestBody;
    }

    private static ArrayList<ConditionValue> getDayConditionValues() {
        ArrayList<ConditionValue> conditionValues = new ArrayList<>();

        for (int i = 1; i <= 31; i++) {
            ConditionValue value = new ConditionValue();
            value.setUserEnteredValue(String.valueOf(i));
            conditionValues.add(value);
        }

        return conditionValues;
    }
}
