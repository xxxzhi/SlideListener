DirectionSlideListenerLayout
==================

好久好久以前模仿QQ邮箱滑动退出写的一个滑动检测的库，看到了决定还是重新修改一下，也重新下熟悉一下事件派发的细节。

这里面主要是一个DirectionSlideListenerLayout，检测滑动事件，当有指定方向的滑动事件发生后，会回调设置的回调函数。
可以在回调接口中finish掉Activity，或者remove Fragment。

## GIF示例
![alt tag](https://raw.githubusercontent.com/xxxzhi/SlideListener/master/raw/slide.gif)


## 使用

可以直接将app/src/main/java/com/houzhi/slidelistener/widget/DirectionSlideListenerLayout.java拷贝到项目中直接使用。

### 代码示例

```
@Nullable
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
  View view = inflater.inflate(R.layout.fragment_scrollview, container, false);
  DirectionSlideListenerLayout slideLayout = new DirectionSlideListenerLayout(getActivity());
  slideLayout.addView(view);
  slideLayout.setDirection(DirectionSlideListenerLayout.SlideDirection.BOTTOM); //往下的方向

  slideLayout.setOnSlideListener(new DirectionSlideListenerLayout.OnDirectionSlideListener() {

      @Override
      public void onDirectionSlide() {
        //指定方向已经有了滑动时间
      }
  });


  slideLayout.setBackgroundColor(bgColor);

  return slideLayout;

}
```

