package com.yuemai.game34999.presentaion.ui.news.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.core.Constants;
import com.yuemai.game34999.data.bean.Article;
import com.yuemai.game34999.data.bean.ArticleCommentDetails;
import com.yuemai.game34999.data.bean.Comment;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.contract.ArticleContract;
import com.yuemai.game34999.presentaion.image.ImageLoader;
import com.yuemai.game34999.presentaion.presenter.news.ArticlePresenter;
import com.yuemai.game34999.presentaion.ui.game.activity.UserCommentActivity;
import com.yuemai.game34999.presentaion.ui.news.adapter.ArticleCommentAdapter;
import com.yuemai.game34999.util.ThreadUtils;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/4  17:59
 * @email : 923080261@qq.com
 * @description :
 */
public class ArticleActivity extends AbstractMvpLoadPagerActivity<ArticlePresenter> implements ArticleContract.View {
    private static final String KEY_ARTICLE_ID = "ARTICLE_ID";

    private int articleId = -1;
    private String imgUrl = null;
    @BindView(R.id.news_detail_photo_iv)
    ImageView ivBg;
    @BindView(R.id.wv_article)
    WebView mWebView;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_publishTime)
    TextView tvPublishTime;

    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.view_toolbar)
    Toolbar viewToolbar;
    @BindView(R.id.clp_toolbar)
    CollapsingToolbarLayout clpToolbar;
    @BindView(R.id.nsv_scroller)
    NestedScrollView nsvScroller;
    @BindView(R.id.ll_detail_bottom)
    FrameLayout llDetailBottom;
    @BindView(R.id.tv_appraise)
    TextView tvAppraise;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.rv_comment)
    RecyclerView mRecyclerView;

    ArticleCommentAdapter mAdapter;

    private long topic_id = -1;

    boolean isBottomShow = true;
    public static final String MIME_TYPE = "text/html; charset=utf-8";
    public static final String ENCODING = "utf-8";

    public static void start(Context context, int articleId, String imgUrl) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(KEY_ARTICLE_ID, articleId);
        intent.putExtra(Constants.ARTICLE_IMG_RES, imgUrl);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setImmerseBar();
        webSettings();
        articleId = getIntent().getIntExtra(Constants.ARTICLE_ID, articleId);
        imgUrl = getIntent().getStringExtra(Constants.ARTICLE_IMG_RES);
        presenter.loadArticle(articleId);
        presenter.loadArticleCommentDetails(articleId);
        ImageLoader.loadNewsBigImage(ArticleActivity.this, imgUrl, ivBg);
        nsvScroller.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY - oldScrollY > 0 && isBottomShow) {
                //下移隐藏
                isBottomShow = false;
                llDetailBottom.animate().translationY(llDetailBottom.getHeight());
            } else if (scrollY - oldScrollY < 0 && !isBottomShow) {
                //上移出现
                isBottomShow = true;
                llDetailBottom.animate().translationY(0);
            }
        });

        viewToolbar.setNavigationIcon(R.drawable.icon_back_white);

        viewToolbar.setNavigationOnClickListener((View v) -> finish());
        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Logger.e("textWatcher--beforeTextChanged s = " + s + "| start = " + start + "| count = " + count + " | after = " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Logger.e("textWatcher--onTextChanged s = " + s + "| start = " + start + "| count = " + count + " | before = " + before);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Logger.e("textWatcher--afterTextChanged s = " + s.toString());
                if (TextUtils.isEmpty(s.toString())) {

                } else {

                }
            }
        });

//        (getWindow().getSharedElementEnterTransition()).addListener(new Transition.TransitionListener() {
//            @Override
//            public void onTransitionStart(Transition transition) {
//            }
//
//            @Override
//            public void onTransitionEnd(Transition transition) {
//                /*
//                 * 测试发现部分手机(如红米note2)上加载图片会变形,没有达到centerCrop效果
//                 * 查阅资料发现Glide配合SharedElementTransition是有坑的,需要在Transition动画结束后再加载图片
//                 * https://github.com/TWiStErRob/glide-support/blob/master/src/glide3/java/com/bumptech/glide/supportapp/github/_847_shared_transition/DetailFragment.java
//                 */
//                isTransitionEnd = true;
//                if (imgUrl != null) {
//                    isImageShow = true;
//                    if (!TextUtils.isEmpty(imgUrl)) {
//                        ImageLoader.loadNewsBigImage(ArticleActivity.this, imgUrl, ivBg);
//                    }
//                }
//            }
//
//            @Override
//            public void onTransitionCancel(Transition transition) {
//            }
//
//            @Override
//            public void onTransitionPause(Transition transition) {
//            }
//
//            @Override
//            public void onTransitionResume(Transition transition) {
//            }
//        });

    }

    @Override
    public void injectThis(ActivityComponent component) {
        component.inject(this);
    }

    /**
     * 入口
     *
     * @param activity
     * @param articleId
     */
    public static void startAction(Activity activity, View view, int articleId, String imgUrl) {
        Intent intent = new Intent(activity, ArticleActivity.class);
        intent.putExtra(Constants.ARTICLE_ID, articleId);
        intent.putExtra(Constants.ARTICLE_IMG_RES, imgUrl);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(activity, view, Constants.TRANSITION_ANIMATION_NEWS_PHOTOS);
            activity.startActivity(intent, options.toBundle());
        } else {

            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(activity, intent, options.toBundle());
        }

    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_news_article;
    }

    @Override
    protected String initTitle() {
        return null;
    }

    private void webSettings() {
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setVerticalScrollbarOverlay(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setHorizontalScrollbarOverlay(false);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(false);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        settings.setDefaultTextEncodingName("utf-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        } else {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }


//        WebSettings webSettings = mWebView.getSettings();
//
//        webSettings.setDatabaseEnabled(true);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setDefaultTextEncodingName("UTF-8");
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setBlockNetworkImage(false);
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        // 设置可以支持缩放
//        webSettings.setSupportZoom(true);
//        // 设置出现缩放工具
//        webSettings.setBuiltInZoomControls(true);
//
//        // Webview自适应屏幕
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//
        // Webview自适应屏幕 4.4以下
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                setWebViewHeight();
//                super.onPageFinished(view, url);
//            }
//        });
//        mWebView.addJavascriptInterface(this, "App");
//
//        mWebView.setWebChromeClient(new WebChromeClient() {
//
//            @Override
//            public void onProgressChanged(WebView view,
//                                          int newProgress) {
//                // 动态在标题栏显示进度条
//                if (newProgress < 100) {
//                    showLoadingView();
//                } else {
//                    {
//                        showSuccessView();
//                    }
//                }
//
//                super.onProgressChanged(view, newProgress);
//            }
//
//            @Override
//            public void onReceivedTitle(WebView view,
//                                        String temp) {
//
//                super.onReceivedTitle(view, temp);
//            }
//
//        });
    }

    @Override
    protected void refreshContentView(View contentView) {
    }

    @Override
    protected int loadDataFromCache() {
        return LoadingPager.LoadingState.stateLoading;
    }

    @Override
    public int loadData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    protected boolean isSteepStatusBar() {
        return true;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showArticle(List<Article> articleList) {
        if (articleList != null && articleList.size() > 0) {
            Article article = articleList.get(0);
            String htmlData = article.getNewsContent();
            //这种写法可以正确解码
            mWebView.loadData(getHtmlData(htmlData), MIME_TYPE, ENCODING);
            tvPublishTime.setText(article.getCreateTime());
            tvAuthor.setText(article.getAuthor());
            clpToolbar.setTitle(article.getNewsTitle());
        }
        ThreadUtils.runOnUiThread(() -> showSuccessView(), 500);
    }

    @Override
    public void showArticleCommentDetails(ArticleCommentDetails articleCommentDetails) {
        if (articleCommentDetails == null) {
            return;
        }
        topic_id = articleCommentDetails.getTopic_id();
        for (Comment comment : articleCommentDetails.getComments()) {
            Logger.e(comment.toString());
        }
        List<Comment> comments = articleCommentDetails.getHots();
        if (comments == null || comments.size() == 0) {
            comments = new ArrayList<>();
            comments.addAll(articleCommentDetails.getComments());
        }
        mAdapter = new ArticleCommentAdapter(comments);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }

    @OnClick({R.id.cv_more, R.id.tv_appraise})
    public void toCommentActivity() {
        UserCommentActivity.start(this, topic_id);
    }

    void setWebViewHeight() {
        mWebView.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
    }

    @JavascriptInterface
    public void resize(final float height) {
        runOnUiThread(() -> {
            Toast.makeText(this, height + "", Toast.LENGTH_LONG).show();
            mWebView.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
        });
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
