import { fetchWithAuth } from './auth.js';

const API_BASE_URL = '/api';
let currentSection = 'day';

export async function createTask(taskData) {
  const response = await fetchWithAuth(`${API_BASE_URL}/tasks`, {
    method: 'POST',
    body: JSON.stringify(taskData)
  });
  return await response.json();
}

export async function getTasks() {
  const response = await fetchWithAuth(`${API_BASE_URL}/tasks`);
  return await response.json();
}

export async function updateTask(id, taskData) {
  const response = await fetchWithAuth(`${API_BASE_URL}/tasks/${id}`, {
    method: 'PATCH',
    body: JSON.stringify(taskData)
  });
  return await response.json();
}

export async function deleteTask(id) {
  await fetchWithAuth(`${API_BASE_URL}/tasks/${id}`, {
    method: 'DELETE'
  });
}

export function renderTasks(tasks) {
  document.querySelectorAll('.notes').forEach(container => {
    container.innerHTML = '';
  });

  tasks.forEach(task => {
    const section = task.section || 'day';
    const container = document.getElementById(`${section}-notes`);
    if (container) {
      container.appendChild(createTaskElement(task));
    }
  });
}

function createTaskElement(task) {
  const element = document.createElement('div');
  element.className = 'note';
  element.dataset.id = task.id;
  element.innerHTML = `
    <strong>${task.title}</strong>
    <p>${task.description}</p>
    <button class="note-delete">×</button>
    <button class="note-edit">✏️</button>
  `;

  element.querySelector('.note-delete').addEventListener('click', () => {
    deleteTask(task.id).then(() => renderTasks());
  });

  element.querySelector('.note-edit').addEventListener('click', () => {
    openEditModal(task);
  });

  return element;
}

export function setupNotesEventListeners() {
  document.querySelectorAll('.add-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      currentSection = btn.closest('.tab').dataset.section;
      document.getElementById('modal').classList.add('show');
    });
  });

  document.getElementById('save-note-btn').addEventListener('click', saveNote);
}

async function saveNote() {
  const title = document.getElementById('note-title').value.trim();
  const description = document.getElementById('note-text').value.trim();

  if (!title || !description) {
    alert('Please fill all fields');
    return;
  }

  try {
    await createTask({ title, description, section: currentSection });
    document.getElementById('modal').classList.remove('show');
    const tasks = await getTasks();
    renderTasks(tasks);
  } catch (error) {
    console.error('Error saving note:', error);
    alert('Failed to save note');
  }
}

function openEditModal(task) {
  currentSection = task.section || 'day';
  document.getElementById('note-title').value = task.title;
  document.getElementById('note-text').value = task.description;
  document.getElementById('modal').classList.add('show');
}