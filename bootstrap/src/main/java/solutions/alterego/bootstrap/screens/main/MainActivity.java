package solutions.alterego.bootstrap.screens.main;

import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import butterknife.OnClick;
import solutions.alterego.bootstrap.App;
import solutions.alterego.bootstrap.R;
import solutions.alterego.bootstrap.core.BaseActivity;

public class MainActivity extends BaseActivity {

    @Inject
    @Named("logger")
    IAndroidLogger mLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        App.component(this).inject(this);
    }

    @OnClick(R.id.button)
    void openMaterialDrawerActivity() {
        startActivity(new Intent(this, SimpleFragmentDrawerActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
