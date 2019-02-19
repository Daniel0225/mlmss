package com.app.mlm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.imageloader.glide.GlideApp;
import com.app.mlm.R;
import com.app.mlm.utils.PhoneUtil;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.widget
 * @fileName : CoustomTopView
 * @date : 2019/1/2  14:20
 * @describe : 顶部可以放静态图片，动态图片，视频的控件
 * @email : xing.luo@taojiji.com
 */
public class CoustomTopView extends RelativeLayout implements PlaybackPreparer, PlayerControlView.VisibilityListener{
    public static final String TYPE_MP4 = "mp4";
    public static final String TYPE_JPG = "jpg";
    private Context mContext;
    private String mSourceUrl;
    private RelativeLayout mRootView;
    private ImageView imageView;
    private PlayerView playerView;
    private int pecentHeight;
    public CoustomTopView(Context context) {
        super(context);
        init(context, null , 0);
    }

    public CoustomTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CoustomTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        this.mContext = context;
        mRootView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.widget_multi_media_view_layout, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CoustomTopView);
        pecentHeight = ta.getInt(R.styleable.CoustomTopView_ctv_height, 25);
        ta.recycle();
        imageView = mRootView.findViewById(R.id.imageView);
        playerView = mRootView.findViewById(R.id.playView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        //设置控件高度为四分之一屏幕高度
        int height = PhoneUtil.getDisplayHeight(mContext) * pecentHeight / 100;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

   public void setData(String type, String url){
        this.mSourceUrl = url;
        if(type.equals(TYPE_JPG)){
            initJpgAndGif();
        }else {
            initVedio();
        }
   }

    private void initJpgAndGif() {
        imageView.setVisibility(View.VISIBLE);
        GlideApp
            .with(mContext)
            .load(mSourceUrl)
            .centerCrop()
            .into(imageView);
    }

    private void initVedio() {
        playerView.setVisibility(View.VISIBLE);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(mContext,
            new DefaultRenderersFactory(mContext),
            new DefaultTrackSelector(),
            new DefaultLoadControl());
        playerView.setPlayer(player);
        Uri uri = Uri.parse(mSourceUrl);
        MediaSource mediaSource = buildMediaSource(uri);
//        player.prepare(mediaSource);
        prepareExoPlayerFromFileUri(player, uri);
        player.setPlayWhenReady(true);
        player.setRepeatMode(Player.REPEAT_MODE_ALL);
    }

    private void prepareExoPlayerFromFileUri(SimpleExoPlayer player, Uri uri) {

        DataSpec dataSpec = new DataSpec(uri);
        final FileDataSource fileDataSource = new FileDataSource();
        try {
            fileDataSource.open(dataSpec);
        } catch (FileDataSource.FileDataSourceException e) {
            e.printStackTrace();
        }

        DataSource.Factory factory = new DataSource.Factory() {
            @Override
            public DataSource createDataSource() {
                return fileDataSource;
            }
        };
        MediaSource audioSource = new ExtractorMediaSource(fileDataSource.getUri(),
                factory, new DefaultExtractorsFactory(), null, null);

        player.prepare(audioSource);
        player.setPlayWhenReady(true);
    }

    @Override
    public void preparePlayback() {

    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
            new DefaultHttpDataSourceFactory("exoplayer-codelab")).
            createMediaSource(uri);
    }

    @Override
    public void onVisibilityChange(int visibility) {

    }
}
