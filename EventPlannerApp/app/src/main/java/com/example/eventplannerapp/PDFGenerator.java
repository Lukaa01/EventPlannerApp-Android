package com.example.eventplannerapp;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PDFGenerator {

    public static void exportPriceListToPDF(ArrayList<Product> productList, Context context) {
        PdfDocument pdfDocument = new PdfDocument();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        int x = 10, y = 25;

        paint.setTextSize(16);
        paint.setFakeBoldText(true);
        canvas.drawText("Cenovnik:", x, y, paint);
        y += paint.descent() - paint.ascent() + 20;

        paint.setTextSize(12);
        paint.setFakeBoldText(false);

        for (Product product : productList) {
            canvas.drawText("Naziv: " + product.getName(), x, y, paint);
            y += paint.descent() - paint.ascent();
            canvas.drawText("Cena: " + product.getPrice(), x, y, paint);
            y += paint.descent() - paint.ascent();
            canvas.drawText("Popust: " + product.getDiscount() + "%", x, y, paint);
            y += paint.descent() - paint.ascent();
            canvas.drawText("Cena sa popustom: " + product.getDiscountedPrice(), x, y, paint);
            y += paint.descent() - paint.ascent() + 20;
        }

        pdfDocument.finishPage(page);
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "CenovnikProizvodi.pdf");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

        Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), contentValues);
        try {
            if (uri != null) {
                OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
                if (outputStream != null) {
                    pdfDocument.writeTo(outputStream);
                    outputStream.close();
                    Toast.makeText(context, "PDF saved to " + uri.getPath(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failed to create output stream", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, "Failed to create URI", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Log.e("PDF", "Error writing PDF", e);
            Toast.makeText(context, "Error saving PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        pdfDocument.close();
    }

    public static void exportServicePriceListToPDF(ArrayList<Service> productList, Context context) {
        PdfDocument pdfDocument = new PdfDocument();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        int x = 10, y = 25;

        paint.setTextSize(16);
        paint.setFakeBoldText(true);
        canvas.drawText("Cenovnik:", x, y, paint);
        y += paint.descent() - paint.ascent() + 20;

        paint.setTextSize(12);
        paint.setFakeBoldText(false);

        for (Service product : productList) {
            canvas.drawText("Naziv: " + product.getTitle(), x, y, paint);
            y += paint.descent() - paint.ascent();
            canvas.drawText("Cena: " + product.getPrice(), x, y, paint);
            y += paint.descent() - paint.ascent();
            canvas.drawText("Popust: " + product.getDiscount() + "%", x, y, paint);
            y += paint.descent() - paint.ascent();
            canvas.drawText("Cena sa popustom: " + product.getDiscountedPrice(), x, y, paint);
            y += paint.descent() - paint.ascent() + 20;
        }

        pdfDocument.finishPage(page);
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "CenovnikUsluge.pdf");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

        Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), contentValues);
        try {
            if (uri != null) {
                OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
                if (outputStream != null) {
                    pdfDocument.writeTo(outputStream);
                    outputStream.close();
                    Toast.makeText(context, "PDF saved to " + uri.getPath(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failed to create output stream", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, "Failed to create URI", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Log.e("PDF", "Error writing PDF", e);
            Toast.makeText(context, "Error saving PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        pdfDocument.close();
    }
}
