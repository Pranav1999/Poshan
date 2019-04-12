package com.example.sihtry1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.sihtry1.adapters.RcrPastRecordsAdapter;
import com.example.sihtry1.models.Referral;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RcrPastRecordsActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference childref = db.collection("referral");
    private RcrPastRecordsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcr_past_records);
        setupRecyclerView();

    }

    public void setupRecyclerView() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Toast.makeText(RcrPastRecordsActivity.this, "userid" + userId, Toast.LENGTH_SHORT).show();


        Query query = childref.whereEqualTo("rcr_id", userId).orderBy("child_first_name", Query.Direction.ASCENDING);


        FirestoreRecyclerOptions<Referral> options = new FirestoreRecyclerOptions.Builder<Referral>()
                .setQuery(query, Referral.class)
                .build();


        adapter = new RcrPastRecordsAdapter(options);


        RecyclerView recyclerView = findViewById(R.id.recyclerviewrcrpastrecords);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RcrPastRecordsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Referral referral = documentSnapshot.toObject(Referral.class);
                DocumentReference reference = documentSnapshot.getReference();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}


