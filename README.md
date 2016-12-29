# Lazy

[![CI Status](http://img.shields.io/travis/games-neox/Lazy.svg?style=flat)](https://travis-ci.org/games-neox/Lazy)
[![Download](https://api.bintray.com/packages/games-neox/maven/lazy/images/download.svg)](https://bintray.com/games-neox/maven/lazy/_latestVersion)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/games-neox/Lazy/blob/master/LICENSE)

Simple [lazy](https://en.wikipedia.org/wiki/Lazy_initialization) loader for Android.

```java
Lazy<Float> lazy = new Lazy<Float>(new Lazy.ILoader<Float>() {

    @Override
    public Float load() {
        TypedValue outValue = new TypedValue();
        context.getResources().getValue(R.dimen.some_float_value, outValue, false);
        return outValue.getFloat();
    }
});

// ...

float someFloatValue = lazy.fastGet();
```

## Requirements

Minimum supported `Android` version: `16 Jelly Bean`

## Installation

Lazy is available through [jcenter](https://bintray.com/bintray/jcenter). To install it, simply add the following line to your gradle file:

```gradle
dependencies {
    debugCompile 'com.gamesneox.lazy:lazy:0.4.4:debug@aar'
    debugCompile 'com.gamesneox.defence:defence:0.4.1:debug@aar'
    releaseCompile 'com.gamesneox.lazy:lazy:0.4.4:release@aar'
    releaseCompile 'com.gamesneox.defence:defence:0.4.1:release@aar'
}
```

## Author

Games Neox, games.neox@gmail.com

## License

Lazy is available under the MIT license. See the LICENSE file for more info.
