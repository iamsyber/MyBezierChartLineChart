/**
 * Description:
 * Copyright: Copyright(c)
 * Company: 
 * CreateTime: 2019/8/16 16:30
 * <p>
 * author syber
 * version 1.0
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;



public class MyBezierChartLineChart extends View {

    private static final String TAG = "MyBezierChartLineChart";
    private static final int DEFAULT_VERTICAL_LINE_COLOR =Color.parseColor("#50D8D8D8");
    private static final int DEFAULT_X_TEXT_COLOR =Color.parseColor("#CCCCCC");
    private static final int DEFAULT_LEFT_RIGHT_MARGIN = 50;
    private static final int DEFAULT_TOP_MARGIN = 50;
    private static final int DEFAULT_BOTTOM_MARGIN = 50;
    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final int DEFAULT_STROKE_SIZE = 2;

    private Paint mVerticalLinePaint=new Paint();
    private Paint mXTextPaint=new Paint();

    private int mMarginLeftRight; //距离父布局左右间距
    private int mVerticalLineMarginTop; //竖直线条距离父布局上面间距
    private int mVerticalLineMarginBotom; //竖直线条距离父布局下面间距
    private int mVerticalLineColor;//竖直方向线条颜色
    private int verticalLineStartColor;//竖直方向线条开始颜色
    private int verticalLineCenterColor;//竖直方向线条中间颜色
    private int verticalLineEndColor;//竖直方向线条结尾颜色
    private int mVerticalLineStrokeWidth;//竖直方向线条粗细
    private int mXTextColor;//X轴文字颜色
    private int[]   verticalColors=new int[3];//竖直方向线条渐变色数组

    private float mTxtsize;
    // 初始文字的宽度
    private float mStartTxtWidth;
    // 末尾文字的宽度
    private float mENdTxtWidth;
    // 字的高度
    private float mTxtHeight;

    private  String startXText="0Km";
    private  String endXText="2.56Km";


    public MyBezierChartLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainStyledAttrs(attrs);
        initPaints();
    }


    public  void initPaints(){
        mVerticalLinePaint.setColor(mVerticalLineColor);
        mVerticalLinePaint.setStyle(Paint.Style.FILL);
        mVerticalLinePaint.setAntiAlias(true);
        mVerticalLinePaint.setTextSize(dp2px(DEFAULT_TEXT_SIZE));
        mVerticalLinePaint.setStrokeWidth(mVerticalLineStrokeWidth);

        mXTextPaint.setColor(mXTextColor);
        mXTextPaint.setStyle(Paint.Style.FILL);
        mXTextPaint.setAntiAlias(true);
        mXTextPaint.setTextSize(mTxtsize);
        Paint.FontMetrics fm = mXTextPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);  //测量文字高度
    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawHorizonLine(canvas);
        drawXLabel(canvas);
    }

    /**
     *画5条分割线 */
    private void drawHorizonLine(Canvas canvas){

        float intervalx = (getMeasuredWidth()-mMarginLeftRight*2)/4;//每条线段的间隔
        for (int i=0; i<5; i++){
            LinearGradient linearGradient =new LinearGradient(
                    mMarginLeftRight+intervalx*i,
                    mVerticalLineMarginTop,
                    mMarginLeftRight+intervalx*i,
                    getMeasuredHeight()-mVerticalLineMarginBotom
                    , verticalColors,null, Shader.TileMode.CLAMP);
            mVerticalLinePaint.setShader(linearGradient);
            canvas.drawLine(
                    mMarginLeftRight+intervalx*i,
                    mVerticalLineMarginTop,
                    mMarginLeftRight+intervalx*i,
                    getMeasuredHeight()-mVerticalLineMarginBotom,
                    mVerticalLinePaint);
        }
    }


    /**
     *画横坐标上面的文字 */
    private void drawXLabel(Canvas canvas){
        //测量文字宽度
        mStartTxtWidth = mXTextPaint.measureText(startXText, 0, startXText.length());
        canvas.drawText(startXText, mMarginLeftRight - mStartTxtWidth / 2, getMeasuredHeight() -mTxtHeight, mXTextPaint);

        mENdTxtWidth = mXTextPaint.measureText(endXText, 0, endXText.length());
        canvas.drawText(endXText, getMeasuredWidth()-mMarginLeftRight - mENdTxtWidth / 2, getMeasuredHeight() -mTxtHeight, mXTextPaint);
        canvas.save();
    }



    /**
     * 从资源文件获取数据
     *
     * @param attrs
     */
    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyBezierChartLineChart);

        mXTextColor = typedArray
                .getColor(R.styleable.MyBezierChartLineChart_xtext_color, DEFAULT_X_TEXT_COLOR);
        mVerticalLineColor = typedArray
                .getColor(R.styleable.MyBezierChartLineChart_vertical_line_color, DEFAULT_VERTICAL_LINE_COLOR);
        verticalLineStartColor = typedArray
                .getColor(R.styleable.MyBezierChartLineChart_vertical_line_startcolor, DEFAULT_VERTICAL_LINE_COLOR);
        verticalLineCenterColor = typedArray
                .getColor(R.styleable.MyBezierChartLineChart_vertical_line_centercolor, DEFAULT_VERTICAL_LINE_COLOR);
        verticalLineEndColor = typedArray
                .getColor(R.styleable.MyBezierChartLineChart_vertical_line_endcolor, DEFAULT_VERTICAL_LINE_COLOR);
        mMarginLeftRight = (int) typedArray
                .getDimension(R.styleable.MyBezierChartLineChart_left_and_right_margin, DEFAULT_LEFT_RIGHT_MARGIN);
        mVerticalLineMarginTop = (int) typedArray
                .getDimension(R.styleable.MyBezierChartLineChart_vertical_line_top_margin, DEFAULT_TOP_MARGIN);
        mVerticalLineMarginBotom = (int) typedArray
                .getDimension(R.styleable.MyBezierChartLineChart_vertical_line_bottom_margin, DEFAULT_BOTTOM_MARGIN);
        mVerticalLineStrokeWidth = (int) typedArray
                .getDimension(R.styleable.MyBezierChartLineChart_vertical_line_stroke, DEFAULT_STROKE_SIZE);
        mTxtsize = (int) typedArray
                .getDimension(R.styleable.MyBezierChartLineChart_xtext_size, DEFAULT_TEXT_SIZE);
        verticalColors[0]=verticalLineStartColor;
        verticalColors[1]=verticalLineCenterColor;
        verticalColors[2]=verticalLineEndColor;

        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }



}