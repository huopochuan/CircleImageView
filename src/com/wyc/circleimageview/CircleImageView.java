package com.wyc.circleimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView extends ImageView{

	//Ĭ��border���
	private static final int default_border_width=0;
	private static final int default_border_color=Color.BLACK;
	
    private int border_width=default_border_width;
	private int border_color=default_border_color;
	
	//����
	private Paint paint;
	private Bitmap bitmap;
	public CircleImageView(Context context) {
		this(context, null);
		
	}
	public CircleImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		
	}
	public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		//��ȡ�Զ�������
		TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.CircleImageView,
				defStyleAttr,0);
		border_width=a.getDimensionPixelSize(R.styleable.CircleImageView_border_width,
				default_border_width);
		border_color=a.getColor(R.styleable.CircleImageView_border_color, 
				default_border_color);
		
		a.recycle();
		
		paint=new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		paint.setDither(true);  
		paint.setFilterBitmap(false);
	 	paint.setStyle(Paint.Style.FILL); 

	}
	//����Բ��bitmap
	private Bitmap createCircleBitmap(){
	
		Bitmap bmp=Bitmap.createBitmap(getWidth(), getWidth(), Bitmap.Config.ARGB_4444);
		Canvas canvas=new Canvas(bmp);
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
	    p.setColor(0xFF66AAFF);
	    canvas.drawCircle(getWidth()/2, getWidth()/2, getWidth()/2-border_width, p);
	    return bmp;
	}
	public void setBitmap(Bitmap bmp){
		bitmap=bmp;
		//���»���
		invalidate();
	}
    @Override
    protected void onDraw(Canvas canvas) {
      
       Bitmap bm=null;
       Drawable drawable= getDrawable();
       if(drawable!=null)
       {
          BitmapDrawable bd = (BitmapDrawable) drawable;
          bm = bd.getBitmap();
       }
       if(bitmap!=null){
    	   bm=bitmap;
       }
   	   float scaleWidth =(getWidth() )* 1.0f / bm.getWidth();
	
	   // ȡ����Ҫ���ŵ�matrix����
	   Matrix matrix = new Matrix();
	   matrix.postScale(scaleWidth, scaleWidth);

	
	   //��ȡ���ź��ͼƬ
	   Bitmap dest= Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getWidth(), matrix, true);
    
      
        
  	   // ����һ��ͼ�㣬��ͼ������ʾͼ�λ�Ϻ��Ч��
	  //���½�ͼ����ƵĻ��������ɫ����
       int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.MATRIX_SAVE_FLAG |
               Canvas.CLIP_SAVE_FLAG |
               Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
               Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
               Canvas.CLIP_TO_LAYER_SAVE_FLAG);
	  
       canvas.drawBitmap(dest, 0, 0, paint);  
      
       paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));  //��Ϊ�����Ȼ���ͼ����DST_IN
       canvas.drawBitmap(createCircleBitmap(), 0, 0, paint); 
       paint.setXfermode(null);
       paint.setStyle(Paint.Style.STROKE);
       paint.setStrokeWidth(border_width);
       paint.setColor(border_color);
       //������߿�
       canvas.drawCircle(getWidth()/2, getWidth()/2, getWidth()/2-border_width, paint);
       canvas.restoreToCount(sc);
       
	   
    }
    
    
	
}
