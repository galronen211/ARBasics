package com.example.arbasics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.collision.Ray;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.Texture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private Scene scene;
    private Camera camera;
    private ModelRenderable bulletRenderable;
    private int balloonsLeft;
    private Point point;
    private TextView balloonsLeftTxt;
    private TextView lvlText;
    private int lvl;
    private ExecutorService es = Executors.newSingleThreadExecutor();
    private int seconds;
    private TextView timer;
    private boolean done;
    private DatabaseReference database;
    private FirebaseAuth mAuth;
    private long addScore;
    // private Frame frame;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getRealSize(point);
        lvl = 1;
        balloonsLeft = 2;
        seconds = 10;
        timer = findViewById(R.id.timeText);
        done = false;
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        balloonsLeftTxt = findViewById(R.id.ballonsCountText);
        lvlText = findViewById(R.id.lvlText);
        CustomARFragment arFragment =
                (CustomARFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);


        assert arFragment != null;
        scene = arFragment.getArSceneView().getScene();
        camera = scene.getCamera();

        addBalloonsToScene();
        buildBulletModel();


        Button shoot = findViewById(R.id.shootButton);
        startTimer();
        shoot.setOnClickListener(v -> {
            shoot();
        });

        // updateBalloonsText();


    }

    /*
    private void updateBalloonsText() {
        new Thread(() -> {
            runOnUiThread(() -> {
                Session session = null;
                try {
                    session = new Session(this);
                } catch (UnavailableArcoreNotInstalledException | UnavailableApkTooOldException | UnavailableSdkTooOldException | UnavailableDeviceNotCompatibleException e) {
                    e.printStackTrace();
                }
                try {
                    assert session != null;
                    frame = session.update();
                } catch (CameraNotAvailableException e) {
                    e.printStackTrace();
                }

                for(Anchor anchor : frame.getUpdatedAnchors()) {
                    System.out.println(anchor.getCloudAnchorId());
                }

            });
        }).start();
    }
    */

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    private void shoot() {

        Ray ray = camera.screenPointToRay(point.x / 2f, point.y / 2f);
        Node node = new Node();
        node.setRenderable(bulletRenderable);
        scene.addChild(node);

        new Thread(() -> {

            for (int i = 0; i < 200; i++) {

                int finalI = i;
                runOnUiThread(() -> {

                    Vector3 vector3 = ray.getPoint(finalI * 0.1f);
                    node.setWorldPosition(vector3);

                    Node nodeInContact = scene.overlapTest(node);

                    if (nodeInContact != null) {

                        balloonsLeft--;
                        addScore++;
                        balloonsLeftTxt.setText("Score: " + addScore);
                        scene.removeChild(nodeInContact);
                        if (balloonsLeft == 0) {
                            done = true;
                            lvl++;
                            balloonsLeft = 2 + lvl / 2;
                            seconds = 10 + lvl / 2;
                            timer.setText((seconds / 60) + ":" + (seconds % 60));
                            addBalloonsToScene();
                            lvlText.setText("Level " + lvl);
                            done = false;
                            startTimer();
                        }
                    }

                });

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            runOnUiThread(() -> scene.removeChild(node));

        }).start();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    private void startTimer() {
        Runnable runnable = () -> {
            timer = findViewById(R.id.timeText);
            while (balloonsLeft > 0 && seconds > 0 && !done) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                seconds--;

                int minutesPassed = seconds / 60;
                int secondsPassed = seconds % 60;

                runOnUiThread(() -> timer.setText(minutesPassed + ":" + secondsPassed));
            }
            if (seconds <= 0) {
                es.shutdownNow();
                database = FirebaseDatabase.getInstance().getReference("Users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                final boolean[] done = {false};
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!done[0]) {
                            String name = (String) dataSnapshot.child("name").getValue();
                            long games = (long) dataSnapshot.child("games").getValue();
                            long score = (long) dataSnapshot.child("score").getValue();
                            database.setValue(new User(name, games + 1, score + addScore));
                            done[0] = true;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                startActivity(new Intent(this, FinishActivity.class));
                finish();
            }
        };
        es.submit(runnable);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void buildBulletModel() {

        Texture
                .builder()
                .setSource(this, R.drawable.texture)
                .build()
                .thenAccept(texture -> {


                    MaterialFactory
                            .makeOpaqueWithTexture(this, texture)
                            .thenAccept(material -> {

                                bulletRenderable = ShapeFactory
                                        .makeSphere(0.01f,
                                                new Vector3(0f, 0f, 0f),
                                                material);

                            });


                });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addBalloonsToScene() {

        ModelRenderable
                .builder()
                .setSource(this, R.raw.balloon)
                .build()
                .thenAccept(renderable -> {

                    for (int i = 0; i < balloonsLeft; i++) {

                        Node node = new Node();
                        node.setRenderable(renderable);
                        scene.addChild(node);
                        Random random = new Random();
                        node.setWorldPosition(new Vector3(
                                (float) random.nextInt(10),
                                random.nextInt(10) / 10f,
                                (float) -random.nextInt(10) - 5
                        ));
                        while (scene.overlapTest(node) != null) {
                            node.setWorldPosition(new Vector3(
                                    (float) random.nextInt(3),
                                    random.nextInt(5) + 5 / 10f,
                                    (float) -random.nextInt(5)
                            ));
                        }
                    }

                });
    }
}