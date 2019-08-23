

/**
 * Description:
 * Copyright: Copyright(c)
 * CreateTime: 2019/8/16 16:30
 * <p>
 * author xubowen
 * version 1.0
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private Paint mBezierPaint=new Paint();

    private int mMarginLeftRight; //距离父布局左右间距
    private int mVerticalLineMarginTop; //竖直线条距离父布局上面间距
    private int mVerticalLineMarginBotom; //竖直线条距离父布局下面间距
    private int mVerticalLineColor;//竖直方向线条颜色
    private int verticalLineStartColor;//竖直方向线条开始颜色
    private int verticalLineCenterColor;//竖直方向线条中间颜色
    private int verticalLineEndColor;//竖直方向线条结尾颜色
    private int mVerticalLineStrokeWidth;//竖直方向线条粗细
    private int mBezierLineStrokeWidth;//竖直方向线条粗细
    private int mXTextColor;//X轴文字颜色
    private int mBezierLineColor;//X轴文字颜色
    private int[]   verticalColors=new int[3];//竖直方向线条渐变色数组

    private float mTxtsize;
    // 初始文字的宽度
    private float mStartTxtWidth;
    // 末尾文字的宽度
    private float mENdTxtWidth;
    // 字的高度
    private float mTxtHeight;
    private  int layoutWidth;
    private  int layoutheight;

    private  String startXText="0Km";
    private  String endXText="2.56Km";
    //基础点数据集合，数据格式可以自己定义 ，这里的ToStartDistance作为X轴方向的值，Y轴的值用ToStartDistance/ToStartDuration计算出来
    private List<RunDataPoint> mRunDataPointList;
    private List<Double> mXDataList=new ArrayList<>();  //X轴方向的数据
    private List<Double> mYDataList=new ArrayList<>();//Y轴方向的数据
    private List<PointF> pointList=new ArrayList<>();
    private List<BezierLineData> mBezierLineDataList=new ArrayList<>();
    private LineDataSet mLineDataSet=new LineDataSet();


    private int[] mGradientColors = new int[]{getResources().getColor(R.color.run_chart_line_start_color),
            getResources().getColor(R.color.run_chart_line_center_color),
            getResources().getColor(R.color.run_chart_line_end_color)};

    public MyBezierChartLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainStyledAttrs(attrs);
        initPaints();
        initLineDataSet();
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

        mBezierPaint.setColor(mBezierLineColor);
        mBezierPaint.setStyle(Paint.Style.STROKE);
        mBezierPaint.setAntiAlias(true);
        mBezierPaint.setStrokeJoin(Paint.Join.ROUND);
        mBezierPaint.setStrokeCap(Paint.Cap.ROUND);
        mBezierPaint.setStrokeWidth(mBezierLineStrokeWidth);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawHorizonLine(canvas);
        drawXLabel(canvas);
        drawBezier(canvas);
    }

    /**
     *画5条分割线 */
    private void drawHorizonLine(Canvas canvas){
        layoutWidth=getMeasuredWidth();
        layoutheight=getMeasuredHeight();

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
     *画贝塞尔曲线
     * @param canvas
     */
    private void drawBezier(Canvas canvas) {
        mBezierLineDataList=getLineData(changePoint(pointList));
        Path bezierPath = new Path();//曲线路径
        bezierPath.moveTo(mBezierLineDataList.get(0).getStartP().x, mBezierLineDataList.get(0).getStartP().y);//移动到起点
        //循环绘制路径
        for (int j = 0; j < mBezierLineDataList.size(); j++) {
            bezierPath.cubicTo(
                    mBezierLineDataList.get(j).getCp1().x, mBezierLineDataList.get(j).getCp1().y,
                    mBezierLineDataList.get(j).getCp2().x, mBezierLineDataList.get(j).getCp2().y,
                    mBezierLineDataList.get(j).getEndP().x, mBezierLineDataList.get(j).getEndP().y);
        }
        //设置颜色和渐变
        int lineColor = mLineDataSet.getColor();
        mBezierPaint.setColor(lineColor);
        LinearGradient mLinearGradient;
        int[] colorArr;
        if (mLineDataSet.getGradientColors() != null) {
            colorArr = mLineDataSet.getGradientColors();
        } else {
            colorArr = new int[]{lineColor, lineColor, lineColor, lineColor, lineColor};
        }
        mLinearGradient = new LinearGradient(
                0,
                mVerticalLineMarginTop,
                0,
                getMeasuredHeight(),
                colorArr,
                null,
                Shader.TileMode.CLAMP
        );
        mBezierPaint.setShader(mLinearGradient);
        canvas.drawPath(bezierPath, mBezierPaint);
        canvas.save();
    }


    /**
     * 设置数据*/
    public void setDataList(List<RunDataPoint> mRunDataPointList, RunDataNormal runDataNormal){
        this.mRunDataPointList = mRunDataPointList;
        String sdis = String.format(Locale.CHINESE,"%.2f",Math.floor(runDataNormal.getTotalDistance() / 10)/100);
        endXText=sdis+"Km";
        initData();
        postInvalidateDelayed(50);
    }

    /**
     * 设置显示的X，Y坐标 因为从外部传来的数据有可能会很多记录几百几千个，因此5个点取一次数据
     */
    private void initData(){
        if(mRunDataPointList.size()>5){
            for (int i=0; i<mRunDataPointList.size()-5; i = i+5){
                double  distance=mRunDataPointList.get(i).getToStartDistance();
                double  duration=mRunDataPointList.get(i).getToStartDuration();
                double realSpeed;
                if(duration==0){
                    realSpeed= 0.0;
                }else{
                    realSpeed= 3.6*distance/duration;
                }
                String formatSpeedStr = String.format(Locale.CHINESE,"%.2f",realSpeed);
                double formatedSpeed = Double.parseDouble(formatSpeedStr);
                mXDataList.add(distance);
                mYDataList.add(formatedSpeed);
                Float x=(float)distance;
                Float y=(float)formatedSpeed;
                pointList.add(new PointF(x,y));
                MyLogUtil.showLog("initData x is:"+x+"  initData y is:"+y);
            }
        }
    }


    /**
     * 利用三阶贝塞尔曲线，获取每一段曲线所需要的点集，包括开始点，结束点，两个控制点。
     *
     * @param pointList 所有的数据点
     * @return 格式化成需要的贝塞尔曲线点阵列
     */
    private List<BezierLineData> getLineData(List<PointF> pointList){
        float t = 0.5f;//比例
        List<BezierLineData> lineDataList = new ArrayList<>();
        PointF startP;
        PointF endP;
        PointF cp1;
        PointF cp2;
        BezierLineData lineData;
        for (int i = 0; i<pointList.size() - 1;i ++){
            startP = pointList.get(i);
            endP = pointList.get(i+1);
            cp1 = new PointF();
            cp1.x = startP.x + (endP.x-startP.x) * t;
            cp1.y =  startP.y;
            cp2 = new PointF();
            cp2.x = startP.x + (endP.x-startP.x) * (1 - t);
            cp2.y = endP.y;
            lineData = new BezierLineData(startP,endP,cp1,cp2);
            lineDataList.add(lineData);
        }
        return lineDataList;
    }


    /**
     * 把一般坐标转为 Android中的视图坐标**/
    private List<PointF> changePoint(List<PointF> oldPointFs){
        List<PointF> pointFs = new ArrayList<>();
        float maxValueY = 0;
        float yValue;
        for (int i = 0; i < oldPointFs.size(); i++){
            yValue = oldPointFs.get(i).y;
            if (maxValueY < yValue) {
                maxValueY = yValue;
            }
        }
        MyLogUtil.showLog("changePoint: maxValueY = " + maxValueY);
        //间隔，减去某个值是为了空出多余空间，为了画线以外，还要写坐标轴的值，除以坐标轴最大值
        //相当于缩小图像
        int blockCount = oldPointFs.size() - 1;
        float intervalX = (layoutWidth - mMarginLeftRight * 2f)/blockCount;   //每个点的水平间隔
        float intervalY = (layoutheight- mVerticalLineMarginBotom -mVerticalLineMarginTop-mTxtHeight*2)/maxValueY-0f;  //每个单位的竖直距离
        MyLogUtil.showLog("changePoint: height  = " + layoutWidth+"   interval X:"+intervalX+"   interval Y"+intervalY);
        MyLogUtil.showLog("changePoint: maxValueY = " + maxValueY);
        PointF p;
        float x;
        float y;
        for (int i = 0; i< oldPointFs.size(); i++){
            PointF pointF = oldPointFs.get(i);
            //最后的正负值是左移右移
            x = i * intervalX + mMarginLeftRight;
            y = layoutheight-mVerticalLineMarginBotom-(intervalY*pointF.y);
            p = new PointF(x, y);
            MyLogUtil.showLog("changeDPoint: pointFs x  " + x+"   pointFs y:"+y);
            pointFs.add(p);
        }
        return pointFs;
    }


    /**
     * 初始化曲线颜色
     */
    public  void initLineDataSet(){
        LineDataSet lineDataSet;
        lineDataSet = new LineDataSet();
        lineDataSet.setColor(0xFFFF0000);
        lineDataSet.setGradientColors(mGradientColors);
        mLineDataSet=lineDataSet;
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



    /**
     * 从资源文件获取数据
     *
     * @param attrs
     */
    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyBezierChartLineChart);

        mXTextColor = typedArray
                .getColor(R.styleable.MyBezierChartLineChart_xtext_color, DEFAULT_X_TEXT_COLOR);
        mBezierLineColor = typedArray
                .getColor(R.styleable.MyBezierChartLineChart_bezier_line_color, DEFAULT_X_TEXT_COLOR);
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
        mBezierLineStrokeWidth = (int) typedArray
                .getDimension(R.styleable.MyBezierChartLineChart_bezier_line_width, DEFAULT_STROKE_SIZE);
        verticalColors[0]=verticalLineStartColor;
        verticalColors[1]=verticalLineCenterColor;
        verticalColors[2]=verticalLineEndColor;

        typedArray.recycle();
    }

    public class BezierLineData {
        private PointF startP;
        private PointF endP;
        private PointF cp1;
        private PointF cp2;

        public BezierLineData(PointF startP,PointF endP,PointF cp1,PointF cp2){
            this.startP = startP;
            this.endP = endP;
            this.cp1 = cp1;
            this.cp2 = cp2;
        }

        public PointF getStartP() {
            return startP;
        }

        public void setStartP(PointF startP) {
            this.startP = startP;
        }

        public PointF getEndP() {
            return endP;
        }

        public void setEndP(PointF endP) {
            this.endP = endP;
        }

        public PointF getCp1() {
            return cp1;
        }

        public void setCp1(PointF cp1) {
            this.cp1 = cp1;
        }

        public PointF getCp2() {
            return cp2;
        }

        public void setCp2(PointF cp2) {
            this.cp2 = cp2;
        }
    }



    public class LineDataSet {
        private int color;//颜色，
        private int[] gradientColors;//渐变色数组
        private List<PointF> oldPointFsList;//原始点

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int[] getGradientColors() {
            return gradientColors;
        }

        public void setGradientColors(int[] gradientColors) {
            this.gradientColors = gradientColors;
        }

    }

}