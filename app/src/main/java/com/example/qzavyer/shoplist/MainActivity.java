package com.example.qzavyer.shoplist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qzavyer.shoplist.Models.Bill;
import com.example.qzavyer.shoplist.Models.Shop;
import com.example.qzavyer.shoplist.Models.ShopItem;
import com.example.qzavyer.shoplist.Service.BillService;
import com.example.qzavyer.shoplist.Service.ShopItemService;
import com.example.qzavyer.shoplist.Service.ShopService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAdd, btnClear, btnBye;
    ListView lvSimple;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnBye = findViewById(R.id.btnBye);
        btnBye.setOnClickListener(this);

        lvSimple = findViewById(R.id.lvMain);

        lvSimple.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox box = view.findViewById(R.id.cbChecked);
                boolean checked = box.isChecked();
                ShopItemService service = new ShopItemService(context);

                service.setChecked((int) id, checked);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        fillList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
            case R.id.btnClear:
                ShopItemService repository = new ShopItemService(this);

                repository.deleteAll();

                fillList();
                break;
            case R.id.btnBye:
                Intent byeIntent = new Intent(this, ByeActivity.class);
                startActivity(byeIntent);
        }
    }

    private void fillList() {
        ShopItemService service = new ShopItemService(this);

        ArrayList<ShopItem> items = service.all();

        // создаем адаптер
        MyCustomAdapter sAdapter = new MyCustomAdapter(this, R.layout.list_item, items);

        // определяем список и присваиваем ему адаптер
        lvSimple.setAdapter(sAdapter);
    }


    class MyCustomAdapter extends ArrayAdapter<ShopItem> {
        private ArrayList<ShopItem> stateList;
        Context context;

        MyCustomAdapter(Context context, int textViewResourceId, ArrayList<ShopItem> stateList) {
            super(context, textViewResourceId, stateList);
            this.stateList = new ArrayList<>();
            this.stateList.addAll(stateList);
            this.context = context;
        }

        private class ViewHolder {
            CheckBox cbChecked;
            TextView textPrice;
            TextView textShop;
        }

        @SuppressLint({"InflateParams", "SetTextI18n"})
        @NotNull
        @Override
        public View getView(int position, View convertView, @NotNull ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {

                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = vi.inflate(R.layout.list_item, null);

                holder = new ViewHolder();
                holder.cbChecked = convertView.findViewById(R.id.cbChecked);
                holder.textPrice = convertView.findViewById(R.id.textPrice);
                holder.textShop = convertView.findViewById(R.id.textShop);

                convertView.setTag(holder);

                holder.cbChecked.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        int id = (int)cb.getTag();

                        ShopItemService service = new ShopItemService(context);
                        service.setChecked(id, cb.isChecked());
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ShopItem item = stateList.get(position);

            holder.cbChecked.setText(item.getName());
            holder.cbChecked.setTag(item.getId());
            holder.cbChecked.setChecked(item.isChecked());

            BillService byeService = new BillService(context);
            Bill bill = byeService.allLastByGood(item.getGoodId());

            ShopService shopService = new ShopService(context);
            Shop shop = shopService.getById(bill.getShopId());

            String shopName = getString(R.string.empty);
            if(shop!=null){
                shopName = shop.getName();
            }

            holder.textPrice.setText(Double.toString(bill.getPrice()));
            holder.textShop.setText(shopName);

            return convertView;
        }
    }
}