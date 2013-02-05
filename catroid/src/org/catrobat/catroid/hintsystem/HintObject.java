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

import android.graphics.Paint;

/**
 * @author amore
 * 
 */
public class HintObject {

	private int pointerXCoordinate;
	private int pointerYCoordinate;
	private int textXCoordinate;
	private int textYCoordinate;
	private String text;
	private int maxWidth;

	public HintObject(int[] coordinates, String text) {
		this.pointerXCoordinate = ScreenParameters.getInstance().setCoordinatesToDensity(
				coordinates[0] + ScreenParameters.getInstance().getTextMarginLeft(), true);
		this.pointerYCoordinate = ScreenParameters.getInstance().setCoordinatesToDensity(coordinates[1], false);

		this.maxWidth = coordinates[2];
		this.text = addingLineBreaks(text);
		examineTextPositions(coordinates);

	}

	private void examineTextPositions(int[] coordinates) {
		this.textXCoordinate = ScreenParameters.getInstance().setCoordinatesToDensity(
				coordinates[0] - ScreenParameters.getInstance().getTextMarginLeft(), true);

		if ((Hint.getInstance().getScreenHeight() - pointerYCoordinate) < 150) {
			this.textYCoordinate = ScreenParameters.getInstance().setCoordinatesToDensity(
					coordinates[1] - ScreenParameters.getInstance().getTextMarginBottom(), false);
		} else {
			this.textYCoordinate = ScreenParameters.getInstance().setCoordinatesToDensity(
					coordinates[1] + ScreenParameters.getInstance().getTextMarginTop(), false);
		}

	}

	private String addingLineBreaks(String text) {
		int screenWidth = Hint.getInstance().getScreenWidth();
		int marginRight = ScreenParameters.getInstance().getTextMarginRight() * screenWidth / 100;
		int textWidth = maxWidth - marginRight;

		Paint paint = new Paint();
		paint.setTextSize(25);

		String formatedText = "";

		if (paint.measureText(text) < textWidth) {
			formatedText = text;
		} else {
			int lastBlank = 0;
			int firstBlank = 0;
			int currentPosition = 1;
			int flag = 0;
			while (currentPosition < text.length()) {
				if (text.charAt(currentPosition - 1) == ' ') {
					firstBlank = currentPosition;
					for (int i = currentPosition + 1; i < text.length(); i++) {
						if (text.charAt(i) == ' ') {
							lastBlank = i;
							break;
						}
					}

					if (paint.measureText(text.substring(flag, lastBlank)) > textWidth) {
						formatedText += "\n";
						flag = currentPosition;

					}
				}

				if (paint.measureText(text.substring(flag, currentPosition)) < textWidth) {
					formatedText += text.substring(currentPosition - 1, currentPosition);
					currentPosition++;
				}

			}
		}

		return formatedText;
	}

	public int getXCoordinate() {
		return pointerXCoordinate;
	}

	public void setXCoordinate(int x) {
		this.pointerXCoordinate = x;
	}

	public int getYCoordinate() {
		return pointerYCoordinate;
	}

	public void setYCoordinate(int y) {
		this.pointerYCoordinate = y;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getTextXCoordinate() {
		return textXCoordinate;
	}

	public void setTextXCoordinate(int textXCoordinate) {
		this.textXCoordinate = textXCoordinate;
	}

	public int getTextYCoordinate() {
		return textYCoordinate;
	}

	public void setTextYCoordinate(int textYCoordinate) {
		this.textYCoordinate = textYCoordinate;
	}

}
