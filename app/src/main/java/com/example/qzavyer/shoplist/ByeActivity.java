package com.example.qzavyer.shoplist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qzavyer.shoplist.Models.Bill;
import com.example.qzavyer.shoplist.Models.Currency;
import com.example.qzavyer.shoplist.Models.Shop;
import com.example.qzavyer.shoplist.Models.ShopItem;
import com.example.qzavyer.shoplist.Service.BillService;
import com.example.qzavyer.shoplist.Service.CurrencyService;
import com.example.qzavyer.shoplist.Service.ShopItemService;
import com.example.qzavyer.shoplist.Service.ShopService;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ByeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    Button btnSave;
    Button btnAddCurrency;
    Button btnAddItem;
    ListView listAdd;
    AutoCompleteTextView editShop;
    ArrayList<ShopItem> items;
    AutoCompleteTextView editCurrency;
    EditText editDate;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bye);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        btnAddCurrency = findViewById(R.id.btnAddCurrency);
        btnAddCurrency.setOnClickListener(this);

        ShopService shopsRepository = new ShopService(this);
        ArrayList<String> shopNames = shopsRepository.getShopNames();

        ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, shopNames);
        editShop = findViewById(R.id.editShop);
        editShop.setAdapter(namesAdapter);

        listAdd = findViewById(R.id.listAdd);

        ShopItemService service = new ShopItemService(this);
        items = service.allChecked();

        CurrencyService currencyService = new CurrencyService(this);
        ArrayList<String> currencyNames = currencyService.getNames();

        if(currencyNames.size()==0){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            Date date = calendar.getTime();

            Currency currency = new Currency();
            currency.setValue(1);
            currency.setDate(date);
            currency.setCode("RUB");
            currency.setName("Рубль");
            currencyService.add(currency);

            Currency usdCurrency = new Currency();
            usdCurrency.setValue(1);
            usdCurrency.setDate(date);
            usdCurrency.setCode("USD");
            usdCurrency.setName("Доллар");
            currencyService.add(usdCurrency);

            Currency eurCurrency = new Currency();
            eurCurrency.setValue(1);
            eurCurrency.setDate(date);
            eurCurrency.setCode("EUR");
            eurCurrency.setName("Евро");
            currencyService.add(eurCurrency);

            currencyNames = currencyService.getNames();
        }

        BillService billService = new BillService(this);
        String lastCurrencyCode = billService.getLastCurrency();
        Currency lastCurrency = currencyService.getByCode(lastCurrencyCode);

        String lastCurrencyName = "Рубль";

        if(lastCurrency != null) lastCurrencyName = lastCurrency.getName();

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, currencyNames);

        editCurrency = findViewById(R.id.editCurrency);
        editCurrency.setText(lastCurrencyName);
        editCurrency.setOnItemClickListener(this);
        editCurrency.setAdapter(currencyAdapter);

        editDate = findViewById(R.id.editDate);
        editDate.setText(dateFormat.format(new Date()));

        btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(this);

        fillList();
    }

    private void fillList() {
        double sum = 0;

        for(ShopItem item : items){
            sum += item.getPrice() * item.getCount();
        }

        // создаем адаптер
        MyCustomAdapter sAdapter = new MyCustomAdapter(this, R.layout.bye_list_item, items);

        // определяем список и присваиваем ему адаптер
        listAdd.setAdapter(sAdapter);

        TextView textSum = findViewById(R.id.textSum);
        textSum.setText(String.valueOf(sum));
    }

    @Override
    public void onClick(@NotNull View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                ShopItemService shopItemService = new ShopItemService(this);
                shopItemService.deleteAllChecked();

                ArrayList<Bill> bills = new ArrayList<>(items.size());

                String shopName = editShop.getText().toString();

                ShopService shopsRepository = new ShopService(this);
                Shop shop = shopsRepository.getShopByName(shopName);

                CurrencyService currencyService = new CurrencyService(this);
                Currency currency = currencyService.getByName(editCurrency.getText().toString());

                if (shop == null) {
                    shop = new Shop();
                    shop.setName(shopName);

                    shop = shopsRepository.add(shop);
                }

                Date byeDate;

                try {
                    byeDate = dateFormat.parse(editDate.getText().toString());
                } catch (ParseException e) {
                    byeDate = new Date();
                }

                for (ShopItem item : items) {
                    Bill bill = new Bill();

                    item.setPrice(item.getPrice()*currency.getValue());

                    bill.setCount(item.getCount());
                    bill.setDate(byeDate);
                    bill.setGoodId(item.getGoodId());
                    bill.setPrice(item.getPrice());
                    bill.setShopId(shop.getId());
                    bill.setSum(item.getPrice() * item.getCount());
                    bill.setCurrency(currency.getCode());

                    bills.add(bill);
                }

                BillService billService = new BillService(this);
                billService.add(bills);

                finish();
                break;
            case R.id.btnAddCurrency:
                Intent intent = new Intent(this, CurrencyActivity.class);
                startActivity(intent);
                break;
            case R.id.btnAddItem:
                Intent addIntent = new Intent(this, AddActivity.class);
                startActivityForResult(addIntent, 1);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String currencyName = editCurrency.getText().toString();

        CurrencyUpdater updater = new CurrencyUpdater(this);
        updater.update(currencyName, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        int id = data.getIntExtra("id", -1);

        ShopItemService itemService = new ShopItemService(this);
        ShopItem item = itemService.getById(id);

        items.add(item);

        fillList();
    }

    class MyCustomAdapter extends ArrayAdapter<ShopItem>  {
        private ArrayList<ShopItem> stateList;
        Context context;

        MyCustomAdapter(Context context, int textViewResourceId, ArrayList<ShopItem> stateList) {
            super(context, textViewResourceId, stateList);
            this.stateList = new ArrayList<>();
            this.stateList.addAll(stateList);
            this.context = context;
        }

        private class ViewHolder implements TextWatcher {
            private ShopItem item;
            private boolean auto = false;

            TextView textName;
            EditText editPrice;
            EditText editCount;
            EditText editSum;

            void setItem(ShopItem item) {
                this.item = item;

                this.textName.setText(item.getName());
                this.editPrice.setText(String.valueOf(item.getPrice()));
                this.editCount.setText(String.valueOf(item.getCount()));
                this.editSum.setText(String.valueOf(item.getPrice() * item.getCount()));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (auto || s.length() == 0) return;

                auto = true;

                double oldSum = item.getPrice() * item.getCount();

                if (this.editPrice.getText().hashCode() == s.hashCode()) {
                    item.setPrice(Double.parseDouble(s.toString()));

                    this.editSum.setText(String.valueOf(item.getPrice() * item.getCount()));
                } else if (this.editCount.getText().hashCode() == s.hashCode()) {
                    item.setCount(Double.parseDouble(s.toString()));

                    this.editSum.setText(String.valueOf(item.getPrice() * item.getCount()));
                } else if (this.editSum.getText().hashCode() == s.hashCode()) {
                    double sum = Double.parseDouble(s.toString());
                    double count = item.getCount();
                    double price = sum / count;

                    item.setPrice(price);
                    this.editPrice.setText(String.valueOf(price));
                }

                double newSum = item.getPrice() * item.getCount();

                TextView textSum = findViewById(R.id.textSum);
                double sum = Double.parseDouble(textSum.getText().toString());
                sum = sum - oldSum + newSum;

                textSum.setText(String.valueOf(sum));

                auto = false;
            }
        }

        @SuppressLint("InflateParams")
        @NotNull
        @Override
        public View getView(int position, View convertView, @NotNull ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {

                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = vi.inflate(R.layout.bye_list_item, null);

                holder = new ViewHolder();
                holder.textName = convertView.findViewById(R.id.textName);
                holder.editPrice = convertView.findViewById(R.id.editPrice);
                holder.editCount = convertView.findViewById(R.id.editCount);
                holder.editSum = convertView.findViewById(R.id.editSum);

                holder.editPrice.addTextChangedListener(holder);

                holder.editCount.addTextChangedListener(holder);

                holder.editSum.addTextChangedListener(holder);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ShopItem item = stateList.get(position);

            holder.setItem(item);

            return convertView;
        }
    }
}