/*
 * Copyright (c) 2025, Eliemar Bueno
 * All rights reserved.
 *
 * This source code is licensed under the CC BY-NC-SA 4.0 license found in the
 * LICENSE file in the root directory of this source tree.
 */

package br.com.ebueno.stockcontrol.common.v1.exception;

public class DuplicatedItemException extends Exception {
    public DuplicatedItemException(String message){
        super(message);
    }

    public DuplicatedItemException(String item, String field, String fieldValue){
        super(String.format("Exist another {0} with field {1} value as {2}", item, field, fieldValue));
    }
}
