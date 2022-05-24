package com.example.firebasedatabase;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

class users{
    private String pid,pname,items,type,vendor;

    public users(String vendor,String pid, String pname, String items, String type) {
        this.pid = pid;
        this.pname = pname;
        this.items = items;
        this.type = type;
        this.vendor = vendor;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}

public class MainActivity extends AppCompatActivity {
    EditText pid,pname,items,type,vendor;
    Button insert,delete,update,retrive;
    //retrive texts
    TextView vend,id,name,ritem,typ;
    FirebaseDatabase firebaseDatabase; //Instance
    DatabaseReference databaseReference;//Ref
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        //insert
        vendor=findViewById(R.id.vendor);
        pid=findViewById(R.id.pid);
        pname=findViewById(R.id.pname);
        items=findViewById(R.id.item);
        type=findViewById(R.id.type);
        //buttons-4
        insert=findViewById(R.id.button);
        delete=findViewById(R.id.delete);
        update=findViewById(R.id.update);
        retrive=findViewById(R.id.retrive);
        //retrieve
        id=findViewById(R.id.id);
        vend=findViewById(R.id.vend);
        name=findViewById(R.id.name);
        ritem=findViewById(R.id.ritem);
        typ=findViewById(R.id.typ);

        firebaseDatabase=FirebaseDatabase.getInstance();

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference=firebaseDatabase.getReference("database").child(pname.getText().toString());
                users use=new users(vendor.getText().toString(),pid.getText().toString(),pname.getText().toString(),items.getText().toString(),type.getText().toString());
                databaseReference.setValue(use);

            }
        });

        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                databaseReference=firebaseDatabase.getReference("database");
                HashMap<String,Object> hm=new HashMap<>();
                hm.put("vendor",vendor.getText().toString());
                hm.put("pid",pid.getText().toString());
                hm.put("pname",pname.getText().toString());
                hm.put("items",items.getText().toString());
                hm.put("type",type.getText().toString());
                databaseReference.child(pname.getText().toString()).updateChildren(hm);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference=firebaseDatabase.getReference("database");
                databaseReference.child(pname.getText().toString()).removeValue();

            }
        });

        retrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference=firebaseDatabase.getReference("database");
                String na=pname.getText().toString();
                String i=id.getText().toString();
                String ve=vendor.getText().toString();
                String it=items.getText().toString();
                String tp=type.getText().toString();
                String hi="";
                if(na.length()>0){
                    hi+=na;
                }
                else if(i.length()>0){
                    hi+=i;
                }
                else if(ve.length()>0){
                    hi = hi + ve;
                }
                else if(it.length()>0) {
                    hi = hi + it;
                }
                else if(tp.length()>0) {
                    hi = hi + tp;
                }
                databaseReference.child(hi).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if(task.isSuccessful()){
                            DataSnapshot ds=task.getResult();
                            String vndor=String.valueOf(ds.child("vendor").getValue());
                            String pd=String.valueOf(ds.child("pid").getValue());
                            String pame=String.valueOf(ds.child("pname").getValue());
                            String iems=String.valueOf(ds.child("items").getValue());
                            String tpe=String.valueOf(ds.child("type").getValue());
                            vend.setText(vndor);
                            id.setText(pd);
                            name.setText(pame);
                            ritem.setText(iems);
                            typ.setText(tpe);
                            Toast.makeText(MainActivity.this,"User exists and Retrieved Successfully",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(MainActivity.this,"User does not exists",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }}
