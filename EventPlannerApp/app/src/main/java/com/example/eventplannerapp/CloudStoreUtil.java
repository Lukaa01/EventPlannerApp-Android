package com.example.eventplannerapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.eventplannerapp.model.BudgetItem;
import com.example.eventplannerapp.model.Event1;
import com.example.eventplannerapp.model.EventActivity;
import com.example.eventplannerapp.model.EventOrganizer;
import com.example.eventplannerapp.model.Notification;
import com.example.eventplannerapp.model.Guest;
import com.example.eventplannerapp.model.Owner;
import com.example.eventplannerapp.model.Package;
import com.example.eventplannerapp.model.Product;
import com.example.eventplannerapp.model.Category;
import com.example.eventplannerapp.model.EventType;
import com.example.eventplannerapp.model.ProductReservation;
import com.example.eventplannerapp.model.Rating;
import com.example.eventplannerapp.model.RatingReport;
import com.example.eventplannerapp.model.Report;
import com.example.eventplannerapp.model.ReservationStatus;
import com.example.eventplannerapp.model.Service;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CloudStoreUtil {
    public static void insertProduct(Product product){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products")
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("REZ_DB", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("REZ_DB", "Error adding document", e);
                    }
                });
    }

    public static void insertReport(Report report){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reports")
                .add(report)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("REZ_DB", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("REZ_DB", "Error adding document", e);
                    }
                });
    }

    public static void insertEventActivity(EventActivity activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("eventActivities")
                .add(activity)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("REZ_DB", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("REZ_DB", "Error adding document", e);
                    }
                });
    }

    public static CompletableFuture<List<Product>> selectAllProducts(){
        CompletableFuture<List<Product>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Product> productList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                                Product product = document.toObject(Product.class);
                                productList.add(product);
                            }
                            Log.d("REZ_DB", "Niz ispis:" + productList);
                            future.complete(productList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }
    public static CompletableFuture<List<Service>> selectAllServices(){
        CompletableFuture<List<Service>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("services")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Service> serviceList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                                Service service = document.toObject(Service.class);
                                serviceList.add(service);
                            }
                            Log.d("REZ_DB", "Niz ispis:" + serviceList);
                            future.complete(serviceList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }
    public static CompletableFuture<List<Report>> selectAllReports(){
        CompletableFuture<List<Report>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reports")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Report> reportList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                                Report report = document.toObject(Report.class);
                                reportList.add(report);
                            }
                            Log.d("REZ_DB", "Niz ispis:" + reportList);
                            future.complete(reportList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }

    public static void updateProduct(Product product){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("REZ_DB", "Product successfully changed with name " + product.getName() +" "+ product.getId());
        DocumentReference docRef = db.collection("products").document(product.getId());
        docRef.set(product, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "Product successfully changed"))
                .addOnFailureListener(e -> Log.w("REZ_DB", "Error getting documents.", e));
    }

    public static void updateService(Service service){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("REZ_DB", "Product successfully changed with name " + service.getTitle() +" "+ service.getId());
        DocumentReference docRef = db.collection("services").document(service.getId());
        docRef.set(service, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "Product successfully changed"))
                .addOnFailureListener(e -> Log.w("REZ_DB", "Error getting documents.", e));
    }

    public static void updateReportStatus(Report report){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("REZ_DB", "Report successfully changed with name " + report.getReportTime() +" "+ report.getId());
        DocumentReference docRef = db.collection("reports").document(report.getId());
        docRef.set(report, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "Report successfully changed"))
                .addOnFailureListener(e -> Log.w("REZ_DB", "Error getting documents.", e));

    }
    public static void updateNotificationStatus(Notification notification){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("REZ_DB", "Notification successfully changed with name " + notification.getId());
        DocumentReference docRef = db.collection("notifications").document(notification.getId());
        docRef.set(notification, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "Notification successfully changed"))
                .addOnFailureListener(e -> Log.w("REZ_DB", "Error getting documents.", e));

    }

    public static void deleteProduct(String productId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Log.d("REZ_DB", "The productId is: " + productId);
        db.collection("products")
                .document(productId)
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "The product has been deleted"))
                .addOnFailureListener(e -> Log.w("REZ_DB", "Error deleting document.", e));

    }
    public static CompletableFuture<EventOrganizer> selectEventOrganizerById(String id){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("event_organizers").document(id);
        CompletableFuture<EventOrganizer> future = new CompletableFuture<>();
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                EventOrganizer eo = documentSnapshot.toObject(EventOrganizer.class);
                future.complete(eo);
                Log.d("REZ_DB", documentSnapshot.getId() + " => " + documentSnapshot.getData());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                future.completeExceptionally(e);
                Log.w("REZ_DB", "Error getting documents.", e);
            }
        });
        return future;
    }

    public static CompletableFuture<Owner> selectOwnerById(String id){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("owners").document(id);
        CompletableFuture<Owner> future = new CompletableFuture<>();
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Owner owner = documentSnapshot.toObject(Owner.class);
                future.complete(owner);
                Log.d("REZ_DB", documentSnapshot.getId() + " => " + documentSnapshot.getData());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                future.completeExceptionally(e);
                Log.w("REZ_DB", "Error getting documents.", e);
            }
        });
        return future;
    }
    public static void cancelReservations(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("productReservations")
            .whereEqualTo("email", email)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference docRef = document.getReference();
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("reservationStatus", ReservationStatus.CANCELED_BY_ADMIN.name());

                        docRef.update(updates)
                            .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "Reservation successfully updated"))
                            .addOnFailureListener(e -> Log.w("REZ_DB", "Error updating reservation.", e));
                    }
                } else {
                    Log.w("REZ_DB", "Error getting reservations: ", task.getException());
                }
            });
    }

    public static void insertNotification(Notification notification){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notifications")
                .add(notification)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("REZ_DB", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("REZ_DB", "Error adding document", e);
                    }
                });
    }
    public static CompletableFuture<List<Notification>> getUnreadNotifications(String email){
        CompletableFuture<List<Notification>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notifications")
                .whereEqualTo("email", email)
                .whereEqualTo("notificationStatus", NotificationStatus.UNREAD.name())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Notification> notificationList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                                Notification notification = document.toObject(Notification.class);
                                notificationList.add(notification);
                            }
                            Log.d("REZ_DB", "Niz ispis:" + notificationList);
                            future.complete(notificationList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }

    public static CompletableFuture<List<Notification>> getAllNotifications(String email){
        CompletableFuture<List<Notification>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notifications")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Notification> notificationList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                                Notification notification = document.toObject(Notification.class);
                                notificationList.add(notification);
                            }
                            Log.d("REZ_DB", "Niz ispis:" + notificationList);
                            future.complete(notificationList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }


    public static void insertEvent(Event1 event){
        // kreiraj novi objekat klase Product
       // Event1 event1 = new Event1(2L, "PLS Ceremony", "Description 2", "Venue B", "Wedding", "2025-07-15", 500);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        dodaje se novi product u kolekciju "products"
        db.collection("eventsOrg")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("REZ_DB", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("REZ_DB", "Error adding document", e);
                    }
                });
    }
    public static CompletableFuture<List<Event1>> selectAllEvents(){
        CompletableFuture<List<Event1>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("eventsOrg")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Event1> eventsList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                                Event1 event1 = document.toObject(Event1.class);
                                eventsList.add(event1);
                            }
                            Log.d("REZ_DB", "Niz ispis:" + eventsList);
                            future.complete(eventsList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }

    public static void insertBudgetItem(BudgetItem budgetItem ){


        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        dodaje se novi product u kolekciju "products"
        db.collection("budgetItems")
                .add(budgetItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("REZ_DB", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("REZ_DB", "Error adding document", e);
                    }
                });
    }
    public static CompletableFuture<List<BudgetItem>> selectAllBudgetItems(){
        CompletableFuture<List<BudgetItem>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("budgetItems")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<BudgetItem> eventsList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                                BudgetItem event1 = document.toObject(BudgetItem.class);
                                eventsList.add(event1);
                            }
                            Log.d("REZ_DB", "Niz ispis:" + eventsList);
                            future.complete(eventsList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }

    public static void insertRatingReport(RatingReport ratingReport) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ratingReports")
                .add(ratingReport)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("REZ_DB", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("REZ_DB", "Error adding document", e);
                    }
                });
    }

    public static void insertRating(Rating rating) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ratings")
                .add(rating)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("REZ_DB", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("REZ_DB", "Error adding document", e);
                    }
                });
    }

    public static CompletableFuture<List<Rating>> selectAllRatings(){
        CompletableFuture<List<Rating>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ratings")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Rating> ratingList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                                Rating rating = document.toObject(Rating.class);
                                ratingList.add(rating);
                            }
                            Log.d("REZ_DB", "Niz ispis:" + ratingList);
                            future.complete(ratingList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }
    public static CompletableFuture<List<EventActivity>> selectAllEventActivities(){
        CompletableFuture<List<EventActivity>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("eventActivities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<EventActivity> activityList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                                EventActivity ac = document.toObject(EventActivity.class);
                                activityList.add(ac);
                            }
                            Log.d("REZ_DB", "Niz ispis:" + activityList);
                            future.complete(activityList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }

    public static void insertGuest(Guest guest) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("guests")
                .add(guest)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("REZ_DB", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("REZ_DB", "Error adding document", e);
                    }
                });
    }

    public static CompletableFuture<List<Guest>> selectAllGuests(){
        CompletableFuture<List<Guest>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("guests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Guest> guestList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                                Guest guest = document.toObject(Guest.class);
                                guestList.add(guest);
                            }
                            Log.d("REZ_DB", "Niz ispis:" + guestList);
                            future.complete(guestList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }
    public static void deleteGuest(String guestId){
        Log.d("DB", "guest id to be deleted:" + guestId);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Log.d("REZ_DB", "The productId is: " + productId);
        db.collection("guests")
                .document(guestId)
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "The guest has been deleted"))
                .addOnFailureListener(e -> Log.w("REZ_DB", "Error deleting document.", e));

    }

    public static void updateGuest(Guest guest){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("REZ_DB", "Product successfully changed with name " + guest.getGuestName() +" "+ guest.getId());
        DocumentReference docRef = db.collection("guests").document(guest.getId());
        docRef.set(guest, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "Guest successfully changed"))
                .addOnFailureListener(e -> Log.w("REZ_DB", "Error getting documents.", e));
    }



    public static CompletableFuture<List<Owner>> selectAllOwners() {
        CompletableFuture<List<Owner>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ownerRegistrationRequests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Owner> ownersList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Owner owner = document.toObject(Owner.class);
                                ownersList.add(owner);
                            }
                            future.complete(ownersList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }

    public static CompletableFuture<List<Owner>> selectAllOwnersSorted(String sortOrder) {
        CompletableFuture<List<Owner>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("owners")
                .orderBy("timestamp", sortOrder.equals("latest") ? Query.Direction.DESCENDING : Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Owner> owners = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Owner owner = document.toObject(Owner.class);
                            owners.add(owner);
                        }
                        future.complete(owners);
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });
        return future;
    }

    public static void updateRatingReport(RatingReport ratingReport) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("REZ_DB", "Updating rating report with ID: " + ratingReport.getId());

        DocumentReference docRef = db.collection("ratingReports").document(ratingReport.getId());
        docRef.set(ratingReport, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "Rating report successfully updated"))
                .addOnFailureListener(e -> Log.w("REZ_DB", "Error updating rating report", e));
    }

    public static CompletableFuture<List<Category>> selectAllCategories() {
        CompletableFuture<List<Category>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Category> categories = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Category category = document.toObject(Category.class);
                                categories.add(category);
                            }
                            future.complete(categories);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }

    public static CompletableFuture<List<EventType>> selectAllEventTypes() {
        CompletableFuture<List<EventType>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("eventTypes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<EventType> categories = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                EventType eventType = document.toObject(EventType.class);
                                categories.add(eventType);
                            }
                            future.complete(categories);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }

    public static CompletableFuture<List<RatingReport>> selectAllRatingReports(){
        CompletableFuture<List<RatingReport>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ratingReports")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<RatingReport> ratingReportList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                                RatingReport ratingReport = document.toObject(RatingReport.class);
                                ratingReportList.add(ratingReport);
                            }
                            Log.d("REZ_DB", "Niz ispis:" + ratingReportList);
                            future.complete(ratingReportList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }
    public static CompletableFuture<EventOrganizer> selectEventOrganizerByEmail(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventOrganizersRef = db.collection("event_organizers");

        CompletableFuture<EventOrganizer> future = new CompletableFuture<>();

        // Query event organizers collection by email
        eventOrganizersRef.whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    // Check if there's any document matching the query
                    if (!querySnapshot.isEmpty()) {
                        // If yes, retrieve the first document (assuming email is unique)
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                        EventOrganizer eo = documentSnapshot.toObject(EventOrganizer.class);
                        future.complete(eo);
                        Log.d("REZ_DB", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                    } else {
                        // If no document matches the query, complete the future exceptionally
                        future.completeExceptionally(new RuntimeException("No event organizer found with email: " + email));
                        Log.d("REZ_DB", "No event organizer found with email: " + email);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    future.completeExceptionally(e);
                    Log.w("REZ_DB", "Error getting event organizer by email", e);
                });

        return future;
    }

    public static void updateOrganizer(EventOrganizer organizer){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("REZ_DB", "Organizer successfully changed with name " + organizer.getFirstname() +" "+ organizer.getId());
        DocumentReference docRef = db.collection("event_organizers").document(organizer.getId());
        docRef.set(organizer, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "Organizer successfully changed"))
                .addOnFailureListener(e -> Log.w("REZ_DB", "Error getting documents.", e));
    }

    public static CompletableFuture<Owner> selectOwnerByEmail(String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventOrganizersRef = db.collection("owners");

        CompletableFuture<Owner> future = new CompletableFuture<>();

        // Query event organizers collection by email
        eventOrganizersRef.whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    // Check if there's any document matching the query
                    if (!querySnapshot.isEmpty()) {
                        // If yes, retrieve the first document (assuming email is unique)
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                        Owner eo = documentSnapshot.toObject(Owner.class);
                        future.complete(eo);
                        Log.d("REZ_DB", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                    } else {
                        // If no document matches the query, complete the future exceptionally
                        future.completeExceptionally(new RuntimeException("No event organizer found with email: " + email));
                        Log.d("REZ_DB", "No event organizer found with email: " + email);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    future.completeExceptionally(e);
                    Log.w("REZ_DB", "Error getting event organizer by email", e);
                });

        return future;
    }
    public static void updateOwner(Owner owner){
        Log.d("TAG", "U clodu storu sam" );

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("REZ_DB", "Owner successfully changed with name " + owner.getFirstname() +" "+ owner.getId());
        DocumentReference docRef = db.collection("owners").document(owner.getId());
        docRef.set(owner, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "Owner successfully changed"))
                .addOnFailureListener(e -> Log.w("REZ_DB", "Error getting documents.", e));
    }


    public static void deleteRating(String ratingId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ratings")
                .document(ratingId)
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "Rating successfully deleted"))
                .addOnFailureListener(e -> Log.w("REZ_DB", "Error deleting rating", e));
    }

    public static CompletableFuture<List<Product>> selectVisibleProducts() {
        CompletableFuture<List<Product>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products")
                .whereEqualTo("visible", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Product> productList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                                Product product = document.toObject(Product.class);
                                productList.add(product);
                            }
                            Log.d("REZ_DB", "Niz ispis:" + productList);
                            future.complete(productList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }

    public static CompletableFuture<List<Service>> selectVisibleServices() {
        CompletableFuture<List<Service>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("services")
                .whereEqualTo("visible", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Service> serviceList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                                Service service = document.toObject(Service.class);
                                serviceList.add(service);
                            }
                            Log.d("REZ_DB", "Niz ispis:" + serviceList);
                            future.complete(serviceList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }

    public static CompletableFuture<List<Package>> selectAllPackages(){
        CompletableFuture<List<Package>> future = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("packages")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Package> packageList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                                Package p = document.toObject(Package.class);
                                packageList.add(p);
                            }
                            Log.d("REZ_DB", "Niz ispis:" + packageList);
                            future.complete(packageList);
                        } else {
                            future.completeExceptionally(task.getException());
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return future;
    }

}
