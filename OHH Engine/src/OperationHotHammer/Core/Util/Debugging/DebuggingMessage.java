/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Util.Debugging;

/**
 *
 * @author Kaitlyn
 */

abstract class DebuggingMessage {
    public abstract boolean hasMessage();
    public abstract String[] popMessage();
    public abstract String[] peekMessage();
    public abstract int getCount();
}
