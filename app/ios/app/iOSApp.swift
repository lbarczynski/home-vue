import SwiftUI
import app_shared

@main
struct iOSApp: App {
	
	init() {
		ApplicationController.companion.get()
			.onApplicationStart()
	}
	
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
