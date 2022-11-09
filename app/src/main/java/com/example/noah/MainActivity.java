package com.example.noah;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<DoctorModel> fullDocList = new ArrayList<>();
    TextView txtDocList, txtnoPatient, txtQueue;
    int no_of_patient = 5;
    String no_string = "Number of patient in queue: ";
    String no_doc = "Doctor ";
    String avg_consult = " average consulting time is ";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDocList = findViewById(R.id.txt_docList);
        txtQueue = findViewById(R.id.txt_queue);
        txtnoPatient = findViewById(R.id.txt_no_patent);
        txtnoPatient.setText(no_string+no_of_patient);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDoctorDialog();
            }
        });

        Button btn_add_patient = findViewById(R.id.btn_add_patient);
        btn_add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPatientDialog();
            }
        });

        Button btn_queue = findViewById(R.id.btn_queue);
        btn_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fullDocList.size()>0){
                    calculateQueueTime();
                } else {
                    Toast.makeText(MainActivity.this, "Add doctor first", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void calculateQueueTime(){
        int avg_consultTime = 0;
        for(int i = 0; i < fullDocList.size(); i++){
            avg_consultTime = avg_consultTime+(fullDocList.get(i).getAvg_time()*no_of_patient);
        }
        int total = avg_consultTime/fullDocList.size();
        int convertTime = total%60;
        txtQueue.setText("Waiting time is: "+convertTime+" minutes");
    }
    private void setDocText(){
        String final_list ="";
        for(int i = 0; i < fullDocList.size(); i++){
            final_list = final_list+no_doc+fullDocList.get(i).getName()+avg_consult+fullDocList.get(i).getAvg_time()+" min\n";
        }
        txtDocList.setText(final_list);
    }

    public void showDoctorDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_doctor_dialog);

        EditText doc_name = dialog.findViewById(R.id.edit_doctor_name);
        EditText avg_time = dialog.findViewById(R.id.edit_avg_time);

        Button add_btn = dialog.findViewById(R.id.btn_add);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(doc_name.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter doctor name", Toast.LENGTH_LONG).show();
                } else if(avg_time.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter Avg timing", Toast.LENGTH_LONG).show();
                } else {
                    String name = doc_name.getText().toString();
                    int avg_time_ = Integer.parseInt(avg_time.getText().toString());
                    fullDocList.add(new DoctorModel(name,avg_time_));
                    setDocText();
                    dialog.dismiss();
                }
            }
        });
        Button cancel = dialog.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showPatientDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_patient);

        EditText no_patient = dialog.findViewById(R.id.edit_no_patient);

        Button add_btn = dialog.findViewById(R.id.add_pt);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(no_patient.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter no of patient", Toast.LENGTH_LONG).show();
                } else {
                    no_of_patient = Integer.parseInt(no_patient.getText().toString());
                    txtnoPatient.setText(no_string+no_of_patient);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
}