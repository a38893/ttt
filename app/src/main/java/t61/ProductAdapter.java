package t61;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ttt.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> mProductList;

    public ProductAdapter(Context mContext, List<Product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderT61 holder;
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_view_t61, parent, false);
            holder = new ViewHolderT61();
            holder.imageView= convertView.findViewById(R.id.iem_product_imageView);
            holder.styleidTV = convertView.findViewById(R.id.item_styleid_textview);
            holder.brandTV = convertView.findViewById(R.id.item_brand_textview);
            holder.priceTV = convertView.findViewById(R.id.item_price_textview);
            holder.additionalInfoTV = convertView.findViewById(R.id.item_additionalInfo_textview);


            convertView.setTag(holder);
        }else {
            holder = (ViewHolderT61) convertView.getTag();
        }
        Product product = mProductList.get(position);
        if (product!=null){
            Picasso.get().load(product.getSearchImage()).into(holder.imageView);
            holder.additionalInfoTV.setText(product.getAdditionalInfo());
            holder.priceTV.setText(product.getPrice());
            holder.brandTV.setText(product.getBrand());
            holder.styleidTV.setText(product.getStyleId());
        }
        return convertView;
    }
    static class ViewHolderT61 {
        ImageView imageView;
        TextView styleidTV, brandTV, priceTV, additionalInfoTV;
    }
}
