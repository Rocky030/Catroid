/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010-2011 The Catroid Team
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid_license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *   
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.tugraz.ist.catroid.uitest.drone;

import android.test.ActivityInstrumentationTestCase2;
import at.tugraz.ist.catroid.ui.MainMenuActivity;

public class TakeOfBrickTest extends ActivityInstrumentationTestCase2<MainMenuActivity> {
	//	private Solo solo;
	//	private static String projectName = "BrickTest";
	//	IDrone droneMock;
	//
	public TakeOfBrickTest() {
		super("at.tugraz.ist.catroid", MainMenuActivity.class);
	}
	//
	//	@Override
	//	public void setUp() throws Exception {
	//		solo = new Solo(getInstrumentation(), getActivity());
	//	}
	//
	//	private void deleteTestProject() {
	//		if (StorageHandler.getInstance().projectExists(projectName)) {
	//			Project delete = StorageHandler.getInstance().loadProject(projectName);
	//			StorageHandler.getInstance().deleteProject(delete);
	//			delete = null;
	//		}
	//	}
	//
	//	@Override
	//	public void tearDown() throws Exception {
	//		try {
	//			solo.finalize();
	//		} catch (Throwable e) {
	//			e.printStackTrace();
	//		}
	//
	//		getActivity().finish();
	//		deleteTestProject();
	//		super.tearDown();
	//	}
	//
	//	public void testLiftoffAndLand() {
	//
	//		solo.clickOnText("New Project");
	//
	//		solo.enterText(0, projectName);
	//
	//		solo.sendKey(Solo.ENTER);
	//
	//		solo.clickOnText("Catroid");
	//
	//		solo.sleep(1000);
	//
	//		solo.clickOnText("Add");
	//
	//		solo.clickOnText("Drone");
	//
	//		solo.clickOnText("take off");
	//
	//		solo.clickOnText("Add");
	//		solo.clickOnText("Drone");
	//		solo.clickOnText("land");
	//
	//		DroneHandler.getInstance().setWasAlreadyConnected();
	//		droneMock = createMock(IDrone.class);
	//
	//		DroneHandler.getInstance().setIDrone(droneMock);
	//
	//		expect(droneMock.getFlyingMode()).andReturn(0);
	//		expect(droneMock.connect()).andReturn(true);
	//
	//		/** expected calls */
	//		droneMock.takeoff();
	//		droneMock.land();
	//		droneMock.emergencyLand();
	//		EasyMock.expectLastCall().anyTimes();
	//		droneMock.disconnect();
	//		EasyMock.expectLastCall().anyTimes();
	//
	//		replay(droneMock);
	//
	//		solo.clickOnText("Start");
	//		solo.sleep(4000);
	//
	//		solo.goBack();
	//		solo.sleep(1000);
	//		solo.goBack();
	//
	//		verify(droneMock);
	//
	//		solo.sleep(1000);
	//		//just for file tests: assertions are covered by verifytest(...)
	//		assertTrue("should not fail", true);
	//	}
}
