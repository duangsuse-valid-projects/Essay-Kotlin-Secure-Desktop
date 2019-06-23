package org.duangsuse.securedesktop.linux

import kotlinx.cinterop.internal.CCall
import platform.posix.printf

private fun println(@CCall.CString msg: String) = printf("%s\n", msg)

fun main() { println("Linux targets are not supported") }