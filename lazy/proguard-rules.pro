#
# Copyright (c) 2016 Games Neox. All rights reserved.
#
# This file is an original work developed by Games Neox
#
# @author Games Neox (games.neox@gmail.com)
#

# GENERAL
-verbose

# SHRINK
-keep public class com.gamesneox.lazy.Lazy { *; }

-keep class com.gamesneox.lazy.Lazy$IGetter { *; }

-keep public class com.gamesneox.lazy.Lazy$ILoader { *; }

-keepattributes InnerClasses,Signature

# OPTIMIZE
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
}

-assumenosideeffects class com.gamesneox.defence.Defence {
    public static void assertNotNull(...);
    public static void assertCondition(...);
}

-assumenosideeffects class java.lang.StringBuilder {
    public <init>(...);
    public *** append(...);
    public *** toString();
}

-optimizationpasses 3

-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*

# OBFUSCATE

# PREVERIFY
-dontpreverify
