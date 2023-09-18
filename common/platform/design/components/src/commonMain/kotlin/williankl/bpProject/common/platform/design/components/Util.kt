package williankl.bpProject.common.platform.design.components

import androidx.compose.runtime.Composable

public typealias ComposableString = @Composable () -> String

public typealias ComposableValue<T> = @Composable () -> T