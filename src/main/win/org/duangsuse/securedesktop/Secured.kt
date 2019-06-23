package org.duangsuse.securedesktop

import platform.windows.*

@UseExperimental(ExperimentalUnsignedTypes::class)
typealias DesktopH = UInt
@UseExperimental(ExperimentalUnsignedTypes::class)
typealias ThrId = UInt

/**
 * Helper for entering protected mode "fake Mixin" (Anti-Keylogger)
 */
abstract class Secured {
    private var savedDesktop: DesktopH = 0
    private var safeDesk: DesktopH = 0

    protected fun enterProtection(newDeskName: String = "safeDesktop") {
        savedDesktop = getdesktop(currthrid())
        safeDesk = mkdesktop(newDeskName, SECURE_DESKTOP_FLAGS)
        switchdesktop(safeDesk)
    }
    protected fun protectNewThread() {
        switchthrdesktop(safeDesk)
    }
    protected fun leaveProtection() {
        switchdesktop(savedDesktop)
    }
    protected fun close() {
        disposedesktop(safeDesk)
    }

    // where
    private fun currthrid() = GetCurrentThreadId()
    private fun mkdesktop(name: String, flags: DesktopH) = CreateDesktopA(name ,null,null,0, flags ,null)
    private fun getdesktop(thr: ThrId): DesktopH = GetDesktop()
    private fun switchdesktop(id: DesktopH) = SwitchDesktop(id)
    private fun switchthrdesktop(id: DesktopH) = SetThreadDesktop(id)
    private fun disposedesktop(id: DesktopH) = CloseDesktop(id)

    companion object Constants {
        const val SECURE_DESKTOP_FLAGS: UInt
                = DESKTOP_READOBJECTS or
                DESKTOP_CREATEWINDOW or
                DESKTOP_CREATEMENU or
                DESKTOP_HOOKCONTROL or
                DESKTOP_JOURNALRECORD or
                DESKTOP_JOURNALPLAYBACK or
                DESKTOP_ENUMERATE or
                DESKTOP_WRITEOBJECTS or
                DESKTOP_SWITCHDESKTOP
    }
}
