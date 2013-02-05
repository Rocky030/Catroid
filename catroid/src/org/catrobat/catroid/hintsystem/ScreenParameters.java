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

/**
 * @author peter
 * 
 */
public class ScreenParameters {
	private static ScreenParameters screenParameters = new ScreenParameters();

	private int textMarginTop = 10;
	private int textMarginBottom = 5;
	private int textMarginLeft = 10;
	private int textMarginRight = 10;

	private ScreenParameters() {

	}

	private enum DENSITY {
		LDPI, MDPI, HDPI, XDPI
	}

	private DENSITY density;

	public static ScreenParameters getInstance() {
		if (screenParameters == null) {
			screenParameters = new ScreenParameters();
		}
		return screenParameters;
	}

	public void setDensityParameter(float density) {
		if (density < 1.0f) {
			this.density = DENSITY.LDPI;
		} else if (density == 1.0f) {
			this.density = DENSITY.MDPI;
		} else if (density == 1.5f) {
			this.density = DENSITY.HDPI;
		} else if (density > 1.5f) {
			this.density = DENSITY.XDPI;
		}
	}

	public DENSITY getDensity() {
		return density;
	}

	public int setCoordinatesToDensity(int value, boolean width) {
		if (value > 100) {
			value = 100;
		}

		if (width) {
			value = (int) ((value / 100.0f) * Hint.getInstance().getScreenWidth());
		} else {

			value = (int) ((value / 100.0f) * Hint.getInstance().getScreenHeight());

		}
		return value;
	}

	public int getTextMarginTop() {
		return textMarginTop;
	}

	public int getTextMarginBottom() {
		return textMarginBottom;
	}

	public int getTextMarginLeft() {
		return textMarginLeft;
	}

	public int getTextMarginRight() {
		return textMarginRight;
	}

}
