/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2012 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.hintsystem;

import java.util.ArrayList;

import org.catrobat.catroid.R;
import org.catrobat.catroid.ui.MyProjectsActivity;
import org.catrobat.catroid.ui.ProgramMenuActivity;
import org.catrobat.catroid.ui.ProjectActivity;
import org.catrobat.catroid.ui.ScriptActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * @author amore
 * 
 */
public class HintController {

	public HintController() {

	}

	public ArrayList<HintObject> getHints(Context context) {
		Activity currentActivity = (Activity) context;
		ArrayList<HintObject> allHints = new ArrayList<HintObject>();
		int[] coord = { 0, 0, 0 };

		if (currentActivity.getLocalClassName().compareTo("ui.MyProjectsActivity") == 0) {
			MyProjectsActivity myProjectsActivity = (MyProjectsActivity) currentActivity;
			coord = examineCoordinates(myProjectsActivity, R.id.my_projects_activity_project_title);
			allHints.add(createHint(coord, "Hier findest du alle Projekte aufgelistet."));

		} else if (currentActivity.getLocalClassName().compareTo("ui.ProjectActivity") == 0) {
			ProjectActivity projectActivity = (ProjectActivity) currentActivity;
			coord = examineCoordinates(projectActivity, R.id.button_add);
			allHints.add(createHint(coord, "Füge einen Sprite hinzu "));
			coord = examineCoordinates(projectActivity, R.id.button_play);
			allHints.add(createHint(coord, "Das Projekt ausführen "));
			coord = examineCoordinates(projectActivity, R.id.sprite_title);
			allHints.add(createHint(coord, "Alle Sprites aufgelistet "));

		} else if (currentActivity.getLocalClassName().compareTo("ui.ProgramMenuActivity") == 0) {
			ProgramMenuActivity programMenuActivity = (ProgramMenuActivity) currentActivity;
			coord = examineCoordinates(programMenuActivity, R.id.program_menu_button_scripts);
			allHints.add(createHint(coord, "Weiter zu den Scripts "));
			coord = examineCoordinates(programMenuActivity, R.id.program_menu_button_costumes);
			allHints.add(createHint(coord, "Weiter zu den Costumes "));
			coord = examineCoordinates(programMenuActivity, R.id.program_menu_button_sounds);
			allHints.add(createHint(coord, "Weiter zu den Sounds "));
			coord = examineCoordinates(programMenuActivity, R.id.button_play);
			allHints.add(createHint(coord, "Das Projekt ausführen "));

		} else if (currentActivity.getLocalClassName().compareTo("ui.ScriptActivity") == 0) {
			ScriptActivity scriptActivity = (ScriptActivity) currentActivity;
			Bundle bundle = scriptActivity.getIntent().getExtras();
			if (bundle.getInt(ScriptActivity.EXTRA_FRAGMENT_POSITION, ScriptActivity.FRAGMENT_SCRIPTS) == 0) {
				coord = examineCoordinates(scriptActivity, R.id.script_fragment_container);
				allHints.add(createHint(coord, "Das sind Bricks "));
				coord = examineCoordinates(scriptActivity, R.id.button_add);
				allHints.add(createHint(coord, "Füge ein Script hinzu "));

			} else if (bundle.getInt(ScriptActivity.EXTRA_FRAGMENT_POSITION, ScriptActivity.FRAGMENT_SCRIPTS) == 1) {
				coord = examineCoordinates(scriptActivity, R.id.script_fragment_container);
				allHints.add(createHint(coord, "Hier sind alle Kostüme "));
				coord = examineCoordinates(scriptActivity, R.id.button_add);
				allHints.add(createHint(coord, "Füge ein Kostüm hinzu "));

			} else if (bundle.getInt(ScriptActivity.EXTRA_FRAGMENT_POSITION, ScriptActivity.FRAGMENT_SCRIPTS) == 2) {
				coord = examineCoordinates(scriptActivity, R.id.script_fragment_container);
				allHints.add(createHint(coord, "Hier sind alle Sounds "));
				coord = examineCoordinates(scriptActivity, R.id.button_add);
				allHints.add(createHint(coord, "Füge einen Sound hinzu "));
			}

			coord = examineCoordinates(scriptActivity, R.id.button_play);
			allHints.add(createHint(coord, "Das Projekt ausführen "));

		}

		return allHints;

	}

	private HintObject createHint(int[] coordinates, String text) {
		HintObject hint = new HintObject(coordinates, text);

		return hint;
	}

	public int[] examineCoordinates(Activity activity, int id) {
		int[] coordinates = { 0, 0, 0 };
		View buttonView = activity.findViewById(id);
		buttonView.getLocationInWindow(coordinates);
		int buttonWidth = buttonView.getWidth();
		int buttonHeight = buttonView.getHeight();
		coordinates[0] = (coordinates[0] + buttonWidth / 2) - 25;
		coordinates[1] = (coordinates[1] + buttonHeight / 2) - 25;
		coordinates[2] = buttonWidth;
		coordinates = normalizeCoordinates(coordinates);

		return coordinates;
	}

	public int[] normalizeCoordinates(int[] coordinates) {
		float x = (float) coordinates[0] / Hint.getInstance().getScreenWidth() * 100;
		float y = (float) coordinates[1] / Hint.getInstance().getScreenHeight() * 100;

		coordinates[0] = (int) x;
		coordinates[1] = (int) y;
		return coordinates;
	}

}
