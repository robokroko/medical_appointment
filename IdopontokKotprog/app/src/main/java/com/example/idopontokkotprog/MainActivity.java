package com.example.idopontokkotprog;

import android.content.Intent;
import android.os.Bundle;

import com.example.idopontokkotprog.adapters.MyListAdapter;
import com.example.idopontokkotprog.models.Appointment;
import com.example.idopontokkotprog.models.Participant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private CollectionReference appointments;
    private ListView appointmentsListView;
    private ArrayList<Appointment> appointmentList;
    MyListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firestore = FirebaseFirestore.getInstance();
        appointments = firestore.collection("Appointment");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditActivity.class);
                intent.putExtra("isNew", true);
                startActivityForResult(intent, 1);
            }
        });

        appointmentList = new ArrayList<>();

        adapter = new MyListAdapter(this, appointmentList);

        refreshList();

        appointmentsListView = findViewById(R.id.list_appointments);
        appointmentsListView.setAdapter(adapter);
        appointmentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Appointment selectedAppointment = appointmentList.get(position);
                Intent intent = new Intent(view.getContext(), EditActivity.class);
                intent.putExtra( "isNew", false);
                intent.putExtra( "status" , selectedAppointment.status);
                intent.putExtra( "appointmentType", selectedAppointment.appointmentType);
                intent.putExtra( "description", selectedAppointment.description);
                intent.putExtra( "start", selectedAppointment.start);
                intent.putExtra( "end", selectedAppointment.end);
                intent.putExtra( "actor", selectedAppointment.participant.actor);
                intent.putExtra( "identifier", selectedAppointment.identifier);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == 1 && resultCode == 1){
            String status = intent.getStringExtra("status");
            String appointmentType = intent.getStringExtra("appointmentType");
            String description = intent.getStringExtra("description");
            String start = intent.getStringExtra("start");
            String end = intent.getStringExtra("end");
            String actor = intent.getStringExtra("participant");

            Participant participant = new Participant("", actor, "", "", "");
            appointments.add(new Appointment("", status, "", "", "", "", appointmentType, "", "", 0, description, "", start, end, 0, "", new Date(), "", "", "", participant, ""));

            refreshList();
        } else if (requestCode == 2 && resultCode == 1){
            String status = intent.getStringExtra("status");
            String appointmentType = intent.getStringExtra("appointmentType");
            String description = intent.getStringExtra("description");
            String start = intent.getStringExtra("start");
            String end = intent.getStringExtra("end");
            String actor = intent.getStringExtra("participant");
            String identifier = intent.getStringExtra("identifier");

            Participant participant = new Participant("", actor, "", "", "");

            DocumentReference updateDocument = appointments.document(identifier);
            updateDocument.update("status",status);
            updateDocument.update("appointmentType",appointmentType);
            updateDocument.update("description",description);
            updateDocument.update("start",start);
            updateDocument.update("end",end);
            updateDocument.update("participant",participant);


            refreshList();
        } else if (requestCode == 2 && resultCode == 2){
            String identifier = intent.getStringExtra("identifier");
            DocumentReference deleteDocument = appointments.document(identifier);
            deleteDocument.delete();

            refreshList();
        }
    }

    private void refreshList(){
        appointments.orderBy("created", Query.Direction.DESCENDING).get().addOnSuccessListener(queryDocumentSnapshots -> {
            appointmentList.clear();
            for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                Appointment appointment = document.toObject(Appointment.class);
                appointment.identifier = document.getId();
                appointmentList.add(appointment);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
