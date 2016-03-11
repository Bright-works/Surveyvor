/**
 * @author 
 * @date 10 mars 2016
 */
package com.surveyvor.exception;

/**
 * @author hanatori
 *
 */
public class FirstArrivedException extends Exception {
		private static final long serialVersionUID = -6833705333904051330L;

		public FirstArrivedException(String value) {
			super(value);
		}

		public FirstArrivedException() {
			super();
		}
}
